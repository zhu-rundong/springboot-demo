package com.zrd.orderservice.orderdetail.service;

/**
 * @InterfaceName OrderDetailMessageService
 * @Description 订单 MQ消息服务类 接口
 * @Author ZRD
 * @Date 2023/3/12
 */
public interface OrderDetailMessageService {

    /**
     * @description 声明消息队列、交换机、绑定、消息的处理
     * @author ZRD
     * @date 2023/3/12
     */
    void handleMessage() throws Exception;
}
