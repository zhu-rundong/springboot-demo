package com.zrd.restaurantservice.rabbitmessge.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.zrd.restaurantservice.rabbitmessge.enums.CommonStatus;
import com.zrd.restaurantservice.product.service.ProductService;
import com.zrd.restaurantservice.product.vo.ProductQueryVo;
import com.zrd.restaurantservice.rabbitmessge.service.OrderDetailMessageService;
import com.zrd.restaurantservice.rabbitmessge.dto.OrderMessageDTO;
import com.zrd.restaurantservice.restaurant.service.RestaurantService;
import com.zrd.restaurantservice.restaurant.vo.RestaurantQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @ClassName OrderDetailMessageServiceIMpl
 * @Description 订单相关MQ业务实现类
 * @Author ZRD
 * @Date 2023/3/19
 **/
@Slf4j
@Service
public class OrderDetailMessageServiceImpl implements OrderDetailMessageService {
    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private ProductService productService;
    @Resource
    private RestaurantService restaurantService;

    @Override
    @Async
    public void handleMessage() throws Exception {
        log.info("--------------------restaurant start listening message---------------------------");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.78.100");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            /*---------------------restaurant---------------------*/
            /* exchangeDeclare(名称,类型,是否持久化,是否自动删除,其他属性) */
            channel.exchangeDeclare(
                    "exchange.order.restaurant",
                    BuiltinExchangeType.DIRECT,
                    true,
                    false,
                    null);

            /* exchangeDeclare(名称,是否持久化,是否独占,是否自动删除,其他属性) */
            channel.queueDeclare(
                    "queue.restaurant",
                    true,
                    false,
                    false,
                    null);
            //绑定
            channel.queueBind(
                    "queue.restaurant",
                    "exchange.order.restaurant",
                    "key.restaurant");
            channel.basicConsume("queue.restaurant", true, deliverCallback, consumerTag -> {});
            while (true) {
                Thread.sleep(100000);
            }
        }
    }


    DeliverCallback deliverCallback =  (consumerTag, message) -> {
        //获取消息
        String messageBody = new String(message.getBody());
        log.info("---------------------->restaurant:deliverCallback:messageBody:{}", messageBody);
        try {
            OrderMessageDTO orderMessageDTO = objectMapper.readValue(messageBody,
                    OrderMessageDTO.class);
            //查询商品信息
            ProductQueryVo productQueryVo = productService.getProductById(orderMessageDTO.getProductId());
            if(Objects.isNull(productQueryVo)){
                orderMessageDTO.setConfirmed(false);
                log.error("The Product does not exist,ProductId:{}",orderMessageDTO.getProductId());
                sendMessage(orderMessageDTO);
                return;
            }
            //查询商家信息
            RestaurantQueryVo restaurantQueryVo = restaurantService.getRestaurantById(productQueryVo.getRestaurantId());
            if(Objects.isNull(restaurantQueryVo)){
                orderMessageDTO.setConfirmed(false);
                log.error("The restaurant does not exist,restaurantId:{}",productQueryVo.getRestaurantId());
                sendMessage(orderMessageDTO);
                return;
            }
            //商品和商家均有效
            if(CommonStatus.STATUS_YES.getStatus().equals(productQueryVo.getProductStatus())
                    && CommonStatus.STATUS_YES.getStatus().equals(restaurantQueryVo.getStatus())){
                orderMessageDTO.setConfirmed(true);
                orderMessageDTO.setPrice(productQueryVo.getProductPrice());
            }else{
                orderMessageDTO.setConfirmed(false);
            }
            sendMessage(orderMessageDTO);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

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
            channel.basicPublish("exchange.order.restaurant", "key.order", null, messageToSend.getBytes());
        }
    }
}
