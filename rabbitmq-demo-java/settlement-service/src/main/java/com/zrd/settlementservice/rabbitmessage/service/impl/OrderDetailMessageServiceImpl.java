package com.zrd.settlementservice.rabbitmessage.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.zrd.settlementservice.rabbitmessage.enums.CommonStatus;
import com.zrd.settlementservice.rabbitmessage.service.OrderDetailMessageService;
import com.zrd.settlementservice.settlement.dto.OrderMessageDTO;
import com.zrd.settlementservice.settlement.service.SettlementService;
import com.zrd.settlementservice.settlement.vo.SettlementQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @ClassName OrderDetailMessageServiceImpl
 * @Description 结算消息服务-订单业务实现类
 * @Author ZRD
 * @Date 2025/3/29
 **/
@Slf4j
@Service
public class OrderDetailMessageServiceImpl implements OrderDetailMessageService {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SettlementService settlementService;

    DeliverCallback deliverCallback = (consumerTag, message) -> {
        String messageBody = new String(message.getBody());
        log.info("---------------------->settlement:deliverCallback:messageBody:{}", messageBody);
        try {
            OrderMessageDTO orderMessageDTO = objectMapper.readValue(messageBody, OrderMessageDTO.class);
            SettlementQueryVo settlementVo = new SettlementQueryVo();
            settlementVo.setAmount(orderMessageDTO.getPrice());
            settlementVo.setDate(new Timestamp(System.currentTimeMillis()));
            settlementVo.setOrderId(orderMessageDTO.getOrderId());
            Long transactionId = settlementService.settlement(orderMessageDTO.getAccountId(), orderMessageDTO.getPrice());
            if(Objects.isNull(transactionId)){
                settlementVo.setStatus(CommonStatus.STATUS_ON.getStatus());
            }else{
                settlementVo.setStatus(CommonStatus.STATUS_YES.getStatus());
                settlementVo.setTransactionId(transactionId);
            }
            Long settlementId = settlementService.addSettlement(settlementVo);
            orderMessageDTO.setSettlementId(settlementId);
            log.info("handleOrderService:settlementOrderDTO:{}", orderMessageDTO);
            sendMessage(orderMessageDTO);
        } catch (Exception e){
            log.error("-------------------->settlement:deliverCallback:messageBody:{}", ExceptionUtils.getStackTrace(e));
        }
    };
    @Async
    @Override
    public void handleMessage() throws Exception {
        log.info("--------------------settlement start listening message---------------------------");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.78.100");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            /*---------------------restaurant---------------------*/
            /* exchangeDeclare(名称,类型,是否持久化,是否自动删除,其他属性) */
            channel.exchangeDeclare(
                    "exchange.settlement.order",
                    BuiltinExchangeType.FANOUT,
                    true,
                    false,
                    null);

            /* exchangeDeclare(名称,是否持久化,是否独占,是否自动删除,其他属性) */
            channel.queueDeclare(
                    "queue.settlement",
                    true,
                    false,
                    false,
                    null);
            //绑定
            channel.queueBind(
                    "queue.settlement",
                    "exchange.order.settlement",
                    "key.settlement");
            channel.basicConsume("queue.settlement", true, deliverCallback, consumerTag -> {});
            while (true) {
                Thread.sleep(100000);
            }
        }
    }

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
            channel.basicPublish("exchange.settlement.order", "key.settlement", null, messageToSend.getBytes());
        }
    }
}
