package com.zrd.orderservice.orderdetail.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrd.orderservice.orderdetail.dto.OrderMessageDTO;
import com.zrd.orderservice.orderdetail.entity.OrderDetailEntity;
import com.zrd.orderservice.orderdetail.enums.OrderStatus;
import com.zrd.orderservice.orderdetail.mapper.OrderDetailMapper;
import com.zrd.orderservice.orderdetail.service.OrderDetailMessageService;
import com.zrd.orderservice.orderdetail.vo.OrderDetailQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            value = @Queue(value = "queue.order"),
                            exchange = @Exchange(value = "exchange.order.restaurant"),
                            key = "key.order"
                    ),
                    @QueueBinding(
                            value = @Queue(value = "queue.order"),
                            exchange = @Exchange(value = "exchange.order.deliveryman"),
                            key = "key.order"
                    ),
                    //Fanout 绑定中无需指定 key
                    @QueueBinding(
                            value = @Queue(value = "queue.order"),
                            exchange = @Exchange(value = "exchange.settlement.order",type = ExchangeTypes.FANOUT),
                            key = ""
                    ),
                    @QueueBinding(
                            value = @Queue(value = "queue.order"),
                            exchange = @Exchange(value = "exchange.order.reward",type = ExchangeTypes.TOPIC),
                            key = "key.order"
                    )
            }

    )
    public void handleMessage(String messageStr) {
        //获取消息
        log.info("---------------------->order:handleMessage:message:{}",messageStr);
        try {
            OrderMessageDTO orderMessageDTO = objectMapper.readValue(messageStr, OrderMessageDTO.class);
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
                        //商家发送消息给骑手
                        String messageToSendDeliverMan = objectMapper.writeValueAsString(orderMessageDTO);
                        Message message = new Message(messageToSendDeliverMan.getBytes());
                        rabbitTemplate.send("exchange.order.deliveryman", "key.deliveryman", message);
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
                        log.error("RESTAURANT_CONFIRMED Failure");
                        //更新状态为订单创建失败
                        orderDetailVo.setOrderStatus(OrderStatus.ORDER_CREATE_FAILED.getStatus());
                    }else{
                        //更新状态为骑手已确认
                        orderDetailVo.setDeliverymanId(orderMessageDTO.getDeliverymanId());
                        orderDetailVo.setOrderStatus(OrderStatus.DELIVERYMAN_CONFIRMED.getStatus());
                        orderDetailVo.setDeliverymanId(orderDetailVo.getDeliverymanId());
                    }
                    orderDetailVo.setDate(new Timestamp(System.currentTimeMillis()));
                    OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                    BeanUtils.copyProperties(orderDetailVo, orderDetailEntity);
                    orderDetailMapper.updateById(orderDetailEntity);
                    rabbitTemplate.send("exchange.order.settlement", "key.settlement", new Message(objectMapper.writeValueAsString(orderMessageDTO).getBytes()));
                    break;
                case DELIVERYMAN_CONFIRMED:
                    //当前订单状态：骑手已确认
                    if(Objects.isNull(orderMessageDTO.getSettlementId())){
                        log.error("DELIVERYMAN_CONFIRMED Failure");
                        //更新状态为订单创建失败
                        orderDetailVo.setOrderStatus(OrderStatus.ORDER_CREATE_FAILED.getStatus());
                    }else{
                        orderDetailVo.setSettlementId(orderMessageDTO.getSettlementId());
                        orderDetailVo.setOrderStatus(OrderStatus.SETTLEMENT_CONFIRMED.getStatus());
                    }
                    orderDetailVo.setDate(new Timestamp(System.currentTimeMillis()));
                    OrderDetailEntity orderDetailEntity1 = new OrderDetailEntity();
                    BeanUtils.copyProperties(orderDetailVo, orderDetailEntity1);
                    orderDetailMapper.updateById(orderDetailEntity1);
                    rabbitTemplate.send("exchange.order.reward", "key.reward", new Message(objectMapper.writeValueAsString(orderMessageDTO).getBytes()));
                    break;
                case SETTLEMENT_CONFIRMED:
                    //已结算
                    if(Objects.isNull(orderMessageDTO.getRewardId())){
                        log.error("SETTLEMENT_CONFIRMED Failure");
                        orderDetailVo.setOrderStatus(OrderStatus.ORDER_CREATE_FAILED.getStatus());
                    }else{
                        orderDetailVo.setRewardId(orderMessageDTO.getRewardId());
                        orderDetailVo.setOrderStatus(OrderStatus.ORDER_CREATE_SUCCESS.getStatus());
                        OrderDetailEntity orderDetailEntity2 = new OrderDetailEntity();
                        BeanUtils.copyProperties(orderDetailVo, orderDetailEntity2);
                        orderDetailMapper.updateById(orderDetailEntity2);
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
