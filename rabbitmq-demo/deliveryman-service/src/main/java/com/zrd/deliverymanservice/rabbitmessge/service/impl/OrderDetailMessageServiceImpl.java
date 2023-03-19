package com.zrd.deliverymanservice.rabbitmessge.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.zrd.deliverymanservice.deliveryman.param.DeliverymanQueryParam;
import com.zrd.deliverymanservice.deliveryman.service.DeliverymanService;
import com.zrd.deliverymanservice.deliveryman.vo.DeliverymanQueryVo;
import com.zrd.deliverymanservice.rabbitmessge.dto.OrderMessageDTO;
import com.zrd.deliverymanservice.rabbitmessge.enums.CommonStatus;
import com.zrd.deliverymanservice.rabbitmessge.service.OrderDetailMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName OrderDetailMessageServiceIMpl
 * @Description 订单相关MQ业务实现类
 * @Author ZRD
 * @Date 2023/3/19
 **/
@Slf4j
@Service
public class OrderDetailMessageServiceImpl implements OrderDetailMessageService {
    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private DeliverymanService deliverymanService;

    @Override
    @Async
    public void handleMessage() throws Exception {
        log.info("--------------------deliveryman start listening message---------------------------");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.78.100");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            /*---------------------restaurant---------------------*/
            /* exchangeDeclare(名称,类型,是否持久化,是否自动删除,其他属性) */
            channel.exchangeDeclare(
                    "exchange.order.deliveryman",
                    BuiltinExchangeType.DIRECT,
                    true,
                    false,
                    null);

            /* exchangeDeclare(名称,是否持久化,是否独占,是否自动删除,其他属性) */
            channel.queueDeclare(
                    "queue.deliveryman",
                    true,
                    false,
                    false,
                    null);
            //绑定
            channel.queueBind(
                    "queue.deliveryman",
                    "exchange.order.deliveryman",
                    "key.deliveryman");
            channel.basicConsume("queue.deliveryman", true, deliverCallback, consumerTag -> {});
            while (true) {
                Thread.sleep(100000);
            }
        }
    }


    DeliverCallback deliverCallback =  (consumerTag, message) -> {
        //获取消息
        String messageBody = new String(message.getBody());
        log.info("---------------------->deliveryman:deliverCallback:messageBody:{}", messageBody);
        try {
            OrderMessageDTO orderMessageDTO = objectMapper.readValue(messageBody,
                    OrderMessageDTO.class);
            //查询有效的骑手
            DeliverymanQueryParam deliverymanQueryParam = new DeliverymanQueryParam();
            deliverymanQueryParam.setStatus(CommonStatus.STATUS_YES.getStatus());
            List<DeliverymanQueryVo> deliverymanList = deliverymanService.getDeliverymanList(deliverymanQueryParam);
            if(CollectionUtils.isEmpty(deliverymanList)){
                log.error("The deliveryman does not exist.");
                sendMessage(orderMessageDTO);
                return;
            }
            //默认取第一个
            orderMessageDTO.setDeliverymanId(deliverymanList.get(0).getId());
            sendMessage(orderMessageDTO);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    /**
     * @description 发送消息
     * @param orderMessageDTO 消息对象
     * @author ZRD
     * @date 2023/3/19
     */
    private void sendMessage(OrderMessageDTO orderMessageDTO) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.78.100");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            String messageToSend = objectMapper.writeValueAsString(orderMessageDTO);
            channel.basicPublish("exchange.order.restaurant", "key.order", null, messageToSend.getBytes());
        }
    }
}
