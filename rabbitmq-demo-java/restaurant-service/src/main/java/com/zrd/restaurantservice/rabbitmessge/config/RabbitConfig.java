package com.zrd.restaurantservice.rabbitmessge.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.zrd.restaurantservice.rabbitmessge.service.OrderDetailMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName RabbitConfig
 * @Description Rabbit 配置类
 * @Author ZRD
 * @Date 2023/3/19
 **/
@Configuration
public class RabbitConfig {
    @Bean
    public Channel rabbitChannel() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.78.100");
        Connection connection = connectionFactory.newConnection();
        return connection.createChannel();
    }
}
