package com.zrd.orderservice.config;

import com.zrd.orderservice.orderdetail.dto.OrderMessageDTO;
import com.zrd.orderservice.orderdetail.service.OrderDetailMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RabbitConfig
 * @Description 订单队列配置
 * @Author ZRD
 * @Date 2023/3/19
 **/
@Configuration
@Slf4j
public class RabbitConfig {
    @Autowired
    private OrderDetailMessageService orderDetailMessageService;


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
    public Exchange exchangeSettlement(){
        //往exchange.order.settlement发消息
        //Fanout 收、发消息不能绑定到同一个exchange
        return new FanoutExchange("exchange.order.settlement");
    }

    @Bean
    public Binding bindingSettlement(){
        //从exchange.settlement.order中收消息
        return new Binding(
                "queue.order",
                Binding.DestinationType.QUEUE,
                "exchange.settlement.order",
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

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(@Autowired CachingConnectionFactory connectionFactory){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.setQueueNames("queue.order");
        simpleMessageListenerContainer.setConcurrentConsumers(3);
        simpleMessageListenerContainer.setMaxConcurrentConsumers(5);
        //手动确认
        //simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //消费端限流，每次只处理一条消息
        simpleMessageListenerContainer.setPrefetchCount(1);
        //消息适配器
        MessageListenerAdapter messageListenerAdapter = getMessageListenerAdapter();
        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);
        return simpleMessageListenerContainer;
    }

    private MessageListenerAdapter getMessageListenerAdapter() {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter();
        //默认调用方法名：ORIGINAL_DEFAULT_LISTENER_METHOD = "handleMessage"
        messageListenerAdapter.setDelegate(orderDetailMessageService);
        //自定义方法名
        /*Map<String,String> methodMapping = new HashMap<>(8);
        methodMapping.put("queue.order","handleMessage");
        messageListenerAdapter.setQueueOrTagToMethodName(methodMapping);*/
        //消息转换器，接收对象
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        jackson2JsonMessageConverter.setClassMapper(new ClassMapper() {
            @Override
            public void fromClass(Class<?> aClass, MessageProperties messageProperties) {}

            @Override
            public Class<?> toClass(MessageProperties messageProperties) {
                return OrderMessageDTO.class;
            }
        });
        messageListenerAdapter.setMessageConverter(jackson2JsonMessageConverter);
        return messageListenerAdapter;
    }
}
