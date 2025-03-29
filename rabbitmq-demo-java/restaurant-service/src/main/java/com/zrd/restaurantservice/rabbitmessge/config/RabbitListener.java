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
public class RabbitListener {
    @Autowired
    private OrderDetailMessageService orderDetailMessageService;

    @Autowired
    public void startListenMessage() throws Exception {
        orderDetailMessageService.handleMessage();
    }
}
