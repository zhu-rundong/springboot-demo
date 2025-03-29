package com.zrd.orderservice.orderdetail.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.zrd.orderservice.orderdetail.dto.OrderMessageDTO;
import com.zrd.orderservice.orderdetail.entity.OrderDetailEntity;
import com.zrd.orderservice.orderdetail.enums.OrderStatus;
import com.zrd.orderservice.orderdetail.mapper.OrderDetailMapper;
import com.zrd.orderservice.orderdetail.service.OrderDetailMessageService;
import com.zrd.orderservice.orderdetail.service.OrderDetailService;
import com.zrd.orderservice.orderdetail.vo.OrderDetailQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    private OrderDetailMapper orderDetailMapper;

   /* @Override
    @Async
    public void handleMessage() throws Exception {
        Thread.sleep(5000);
        log.info("--------------------order start listening message---------------------------");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.78.100");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            //channel.basicConsume("queue.order",true,deliverCallback,consumerTag->{});
            while (true) {
                Thread.sleep(100000);
            }
        }
    }*/


    public void handleMessage(OrderMessageDTO orderMessageDTO) {
        //获取消息
        //String message = new String(messageBody);
        log.info("---------------------->order:handleMessage:message:{}", orderMessageDTO.toString());
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.78.100");
        try {
            //OrderMessageDTO orderMessageDTO = objectMapper.readValue(message, OrderMessageDTO.class);
            //从数据库中获取订单信息
            OrderDetailQueryVo orderDetailVo = orderDetailMapper.getOrderDetailById(orderMessageDTO.getOrderId());
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
                        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                        BeanUtils.copyProperties(orderDetailVo, orderDetailEntity);
                        orderDetailMapper.updateById(orderDetailEntity);
                        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
                            //商家发送消息给骑手
                            String messageToSendDeliverMan = objectMapper.writeValueAsString(orderMessageDTO);
                            channel.basicPublish("exchange.order.deliveryman", "key.deliveryman", null, messageToSendDeliverMan.getBytes());
                        }
                    }else{
                        orderDetailVo.setOrderStatus(OrderStatus.ORDER_CREATE_FAILED.getStatus());
                        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                        BeanUtils.copyProperties(orderDetailVo, orderDetailEntity);
                        orderDetailMapper.updateById(orderDetailEntity);
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
                    OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                    BeanUtils.copyProperties(orderDetailVo, orderDetailEntity);
                    orderDetailMapper.updateById(orderDetailEntity);
                    try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
                        //商家发送消息给结算服务
                        String messageToSendDeliverMan = objectMapper.writeValueAsString(orderMessageDTO);
                        channel.basicPublish("exchange.order.settlement", "key.settlement", null, messageToSendDeliverMan.getBytes());
                    }
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
