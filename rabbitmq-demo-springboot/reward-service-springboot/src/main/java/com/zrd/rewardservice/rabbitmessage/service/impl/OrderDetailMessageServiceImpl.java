package com.zrd.rewardservice.rabbitmessage.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.zrd.rewardservice.rabbitmessage.dto.OrderMessageDTO;
import com.zrd.rewardservice.rabbitmessage.enums.CommonStatus;
import com.zrd.rewardservice.rabbitmessage.service.OrderDetailMessageService;
import com.zrd.rewardservice.reward.service.RewardService;
import com.zrd.rewardservice.reward.vo.RewardQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @ClassName OrderDetailMessageServiceImpl
 * @Description 积分消息服务-订单业务实现类
 * @Author ZRD
 * @Date 2025/3/29
 **/
@Slf4j
@Service
public class OrderDetailMessageServiceImpl implements OrderDetailMessageService {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RewardService rewardService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("queue.reward"),
                    exchange = @Exchange(value = "exchange.order.reward", type = ExchangeTypes.TOPIC),
                    key = "key.reward"
            )
    })
    public void handleMessage(String messageStr) {
        log.info("---------------------->reward:deliverCallback:messageBody:{}", messageStr);
        try {
            OrderMessageDTO orderMessageDTO = objectMapper.readValue(messageStr, OrderMessageDTO.class);
            RewardQueryVo rewardQueryVo = new RewardQueryVo();
            rewardQueryVo.setOrderId(orderMessageDTO.getOrderId());
            rewardQueryVo.setAmount(orderMessageDTO.getPrice());
            rewardQueryVo.setStatus(CommonStatus.STATUS_YES.getStatus());
            rewardQueryVo.setDate(new Timestamp(System.currentTimeMillis()));
            Long rewardId = rewardService.addReward(rewardQueryVo);
            if(Objects.nonNull(rewardId)){
                orderMessageDTO.setRewardId(rewardId);
                rabbitTemplate.send("exchange.settlement.order", "", new Message(objectMapper.writeValueAsString(orderMessageDTO).getBytes()));
                //sendMessage(orderMessageDTO);
            }else{
                log.error("-------------------->save reward failure");
            }
        } catch (Exception e){
            log.error("-------------------->reward:deliverCallback:messageBody:{}", ExceptionUtils.getStackTrace(e));
        }
    };
    /*@Async
    @Override
    public void handleMessage() throws Exception {
        log.info("--------------------reward start listening message---------------------------");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.78.100");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            *//*---------------------restaurant---------------------*//*
            *//* exchangeDeclare(名称,类型,是否持久化,是否自动删除,其他属性) *//*
            channel.exchangeDeclare(
                    "exchange.order.reward",
                    BuiltinExchangeType.TOPIC,
                    true,
                    false,
                    null);

            *//* exchangeDeclare(名称,是否持久化,是否独占,是否自动删除,其他属性) *//*
            channel.queueDeclare(
                    "queue.reward",
                    true,
                    false,
                    false,
                    null);
            //绑定
            channel.queueBind(
                    "queue.reward",
                    "exchange.order.reward",
                    "key.reward");
            channel.basicConsume("queue.reward", true, deliverCallback, consumerTag -> {});
            while (true) {
                Thread.sleep(100000);
            }
        }
    }

    *//**
     * @description 发送消息
     * @param orderMessageDTO 消息对象
     * @author ZRD
     * @date 2023/3/19
     *//*
    private void sendMessage(OrderMessageDTO orderMessageDTO) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.78.100");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            String messageToSend = objectMapper.writeValueAsString(orderMessageDTO);
            channel.basicPublish("exchange.order.reward", "key.order", null, messageToSend.getBytes());
        }
    }*/
}
