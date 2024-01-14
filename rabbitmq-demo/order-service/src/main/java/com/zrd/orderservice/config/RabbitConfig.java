package com.zrd.orderservice.config;

import com.zrd.orderservice.orderdetail.service.OrderDetailMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitConfig
 * @Description TODO
 * @Author ZRD
 * @Date 2023/3/19
 **/
@Configuration
@Slf4j
public class RabbitConfig {
    @Autowired
    private OrderDetailMessageService orderDetailMessageService;

    @Autowired
    public void startListenMessage() throws Exception {
        orderDetailMessageService.handleMessage();
    }

    @Bean
    public CachingConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("192.168.78.100");
        //connectionFactory.setPort(15672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //开启发送确认
        //Use with CorrelationData to correlate confirmations with sent messages
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        //消息返回回调
        connectionFactory.setPublisherReturns(true);
        //手动触发
        connectionFactory.createConnection();
        return connectionFactory;
    }
    @Bean
    public RabbitAdmin rabbitAdmin(CachingConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public Exchange exchangeRestaurant(){
        return new DirectExchange("exchange.order.restaurant");
    }
    @Bean
    public Queue queueOrder(){
        return new Queue("queue.order");
    }

    @Bean
    public Binding bindingRestaurant(){
        return new Binding(
                "queue.order",
                Binding.DestinationType.QUEUE,
                "exchange.order.restaurant",
                "key.order",
                null
        );
    }

    @Bean
    public Exchange exchangeDeliveryman(){
        return new DirectExchange("exchange.order.deliveryman");
    }

    @Bean
    public Binding bindingDeliveryman(){
        return new Binding(
                "queue.order",
                Binding.DestinationType.QUEUE,
                "exchange.order.deliveryman",
                "key.order",
                null
        );
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        //消息返回回调
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.info("message:{},exchange:{},routingKey:{}",returnedMessage.getMessage(),returnedMessage.getExchange(),returnedMessage.getRoutingKey());
        });
        //发送确认
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("correlationData:{},ack:{},cause:{}",correlationData,ack,cause);
        });

        return rabbitTemplate;

    }
}
