package com.zrd.orderservice.config;

import com.zrd.orderservice.orderdetail.service.OrderDetailMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitConfig
 * @Description TODO
 * @Author ZRD
 * @Date 2023/3/19
 **/
@Configuration
public class RabbitConfig {
    @Autowired
    private OrderDetailMessageService orderDetailMessageService;

    @Autowired
    public void startListenMessage() throws Exception {
        orderDetailMessageService.handleMessage();
    }
}
