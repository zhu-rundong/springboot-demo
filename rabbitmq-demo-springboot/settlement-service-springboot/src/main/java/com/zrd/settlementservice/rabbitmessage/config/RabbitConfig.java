//package com.zrd.settlementservice.rabbitmessage.config;
//
//import com.zrd.settlementservice.rabbitmessage.service.OrderDetailMessageService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//
///**
// * @ClassName RabbitConfig
// * @Description 结算队列配置
// * @Author ZRD
// * @Date 2023/3/19
// **/
//@Slf4j
//@Configuration
//public class RabbitConfig {
//
//    @Autowired
//    private OrderDetailMessageService orderDetailMessageService;
//
//    @Autowired
//    public void startListenMessage() throws Exception {
//        orderDetailMessageService.handleMessage();
//    }
//}
//
