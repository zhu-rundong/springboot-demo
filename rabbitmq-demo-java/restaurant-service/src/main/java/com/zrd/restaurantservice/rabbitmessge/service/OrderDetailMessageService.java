package com.zrd.restaurantservice.rabbitmessge.service;

/**
 * @InterfaceName OrderDetailMessageService
 * @Description 订单相关MQ业务接口
 * @Author ZRD
 * @Date 2023/3/19
 */
public interface OrderDetailMessageService {

    /**
     * @description 声明消息队列、交换机、绑定、消息的处理
     * @author ZRD
     * @date 2023/3/12
     */
    void handleMessage() throws Exception;
}
