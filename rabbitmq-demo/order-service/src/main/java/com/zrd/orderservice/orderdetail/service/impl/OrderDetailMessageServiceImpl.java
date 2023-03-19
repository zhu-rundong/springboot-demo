package com.zrd.orderservice.orderdetail.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.zrd.orderservice.orderdetail.dto.OrderMessageDTO;
import com.zrd.orderservice.orderdetail.enums.OrderStatus;
import com.zrd.orderservice.orderdetail.service.OrderDetailMessageService;
import com.zrd.orderservice.orderdetail.service.OrderDetailService;
import com.zrd.orderservice.orderdetail.vo.OrderDetailQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * @ClassName OrderDetailMessageServiceImpl
 * @Description 订单 MQ消息服务实现类
 * @Author ZRD
 * @Date 2023/3/12
 **/
@Slf4j
@Service
public class OrderDetailMessageServiceImpl implements OrderDetailMessageService {
    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private OrderDetailService orderDetailService;
    @Override
    @Async
    public void handleMessage() throws Exception {
        log.info("--------------------order start listening message---------------------------");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.78.100");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            /*
             * 监听餐厅、骑手两个微服务发送的消息，餐厅、骑手两个微服务通过两个交换机和一个路由键路由消息到一个队列
             */
            /*---------------------restaurant---------------------*/
            /* exchangeDeclare(名称,类型,是否持久化,是否自动删除,其他属性) */
            channel.exchangeDeclare(
                    "exchange.order.restaurant",
                    BuiltinExchangeType.DIRECT,
                    true,
                    false,
                    null);

            /* exchangeDeclare(名称,是否持久化,是否独占,是否自动删除,其他属性) */
            channel.queueDeclare(
                    "queue.order",
                    true,
                    false,
                    false,
                    null);
            //绑定
            channel.queueBind(
                    "queue.order",
                    "exchange.order.restaurant",
                    "key.order");

            /*---------------------restaurant---------------------*/
            channel.exchangeDeclare(
                    "exchange.order.deliveryman",
                    BuiltinExchangeType.DIRECT,
                    true,
                    false,
                    null);
            //绑定
            channel.queueBind(
                    "queue.order",
                    "exchange.order.deliveryman",
                    "key.order");
            channel.basicConsume("queue.order",true,deliverCallback,consumerTag->{});
            while (true) {
                Thread.sleep(100000);
            }
        }
    }


    DeliverCallback deliverCallback =  (consumerTag, message) -> {
        //获取消息
        String messageBody = new String(message.getBody());
        log.info("---------------------->order:deliverCallback:messageBody:{}", messageBody);
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.78.100");
        try {
            OrderMessageDTO orderMessageDTO = objectMapper.readValue(messageBody,
                    OrderMessageDTO.class);
            //从数据库中获取订单信息
            OrderDetailQueryVo orderDetailVo = orderDetailService.getOrderDetailById(orderMessageDTO.getOrderId());
            OrderStatus orderStatus = OrderStatus.getStatus(orderDetailVo.getOrderStatus());
            if(Objects.isNull(orderStatus)){
                log.error("orderStatus is null,orderId:{}", orderMessageDTO.getOrderId());
                return;
            }
            switch (orderStatus){
                case ORDER_CREATING:
                    //当前订单状态：订单创建中，并且商家已确认商品信息
                    if (orderMessageDTO.getConfirmed() && Objects.nonNull(orderMessageDTO.getPrice())) {
                        //订单商家已确认，并且价格不为空，更新数据库中订单状态为商家已确认
                        orderDetailVo.setOrderStatus(OrderStatus.RESTAURANT_CONFIRMED.getStatus());
                        orderDetailVo.setOrderPrice(orderMessageDTO.getPrice());
                        orderDetailService.updateOrderDetail(orderDetailVo);
                        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
                            //商家发送消息给骑手
                            String messageToSendDeliverMan = objectMapper.writeValueAsString(orderMessageDTO);
                            channel.basicPublish("exchange.order.deliveryman", "key.deliveryman", null,
                                    messageToSendDeliverMan.getBytes());
                        }
                    }
                    break;
                case RESTAURANT_CONFIRMED:
                    //当前订单状态：餐厅已确认，并且已分配骑手，更新状态为订单创建成功
                    if(Objects.isNull(orderMessageDTO.getDeliverymanId())){
                        //更新状态为订单创建失败
                        orderDetailVo.setOrderStatus(OrderStatus.ORDER_CREATE_FAILED.getStatus());
                    }else{
                        //更新状态为订单创建成功
                        orderDetailVo.setDeliverymanId(orderMessageDTO.getDeliverymanId());
                        orderDetailVo.setOrderStatus(OrderStatus.ORDER_CREATE_SUCCESS.getStatus());
                        orderDetailVo.setDeliverymanId(orderDetailVo.getDeliverymanId());
                    }
                    orderDetailVo.setDate(new Timestamp(System.currentTimeMillis()));
                    orderDetailService.updateOrderDetail(orderDetailVo);
                    break;
                default:
                    log.error("error orderStatus:{}",orderStatus);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };
}
