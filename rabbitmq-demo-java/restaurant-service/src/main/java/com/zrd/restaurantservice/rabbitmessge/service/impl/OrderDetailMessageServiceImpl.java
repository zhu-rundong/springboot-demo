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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
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
    @Autowired
    private Channel channel;

    @Override
    @Async
    public void handleMessage() throws Exception {
        log.info("--------------------restaurant start listening message---------------------------");
        /*---------------------接收死信队列的消息---------------------*/
        //死信交换机
        channel.exchangeDeclare(
                "exchange.dlx",
                BuiltinExchangeType.TOPIC,
                true,
                false,
                null
        );
        channel.queueDeclare(
                "queue.dlx",
                true,
                false,
                false,
                null
        );
        channel.queueBind(
                "queue.dlx",
                "exchange.dlx",
                "#"
        );
        Map<String,Object> args = new HashMap<>(16);
        //设置队列中的所有消息的过期时间
        args.put("x-message-ttl",60000);
        //设置队列最大长度
        args.put("x-max-length",5);
        //设置死信队列
        args.put("x-dead-letter-exchange","exchange.dlx");
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
                args);
        //绑定
        channel.queueBind(
                "queue.restaurant",
                "exchange.order.restaurant",
                "key.restaurant");
        //消费端限流，一个消费端，最多推送 prefetchCount 条未确认的消息，
        // Qos（服务质量保证），保证了一定数目的消息未被确认前（必须使用手动确认），不消费新的消息
        //使用消费端限流，当mq所在应用服务集群重启，不会把所有消息都推送到一个应用服务
        channel.basicQos(2);
        //手动ACK，autoAck为false
        channel.basicConsume("queue.restaurant", false, deliverCallback, consumerTag -> {});
        while (true) {
            Thread.sleep(100000);
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
                sendMessage(orderMessageDTO,message);
                return;
            }
            //查询商家信息
            RestaurantQueryVo restaurantQueryVo = restaurantService.getRestaurantById(productQueryVo.getRestaurantId());
            if(Objects.isNull(restaurantQueryVo)){
                orderMessageDTO.setConfirmed(false);
                log.error("The restaurant does not exist,restaurantId:{}",productQueryVo.getRestaurantId());
                sendMessage(orderMessageDTO,message);
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
            sendMessage(orderMessageDTO,message);
        }catch (Exception e) {
            log.error("restaurant:deliverCallback:messageBody:{}", messageBody);
        }
    };

    /**
     * @description 发送消息
     * @param orderMessageDTO 消息对象
     * @author ZRD
     * @date 2023/3/19
     */
    private void sendMessage(OrderMessageDTO orderMessageDTO,Delivery message) throws Exception{
        channel.addReturnListener(returnMessage -> {
            //返回无法路由的消息，处理消息返回后的业务逻辑
            log.info("Return Message:{}",returnMessage);
        });
        //睡眠三秒，模拟业务逻辑处理
        Thread.sleep(3000);
        //消费确认，手动签收消息
        channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        //手动拒收，强制重回队列
        //channel.basicNack(message.getEnvelope().getDeliveryTag(),false,true);
        String messageToSend = objectMapper.writeValueAsString(orderMessageDTO);
        //mandatory 为true，RabbitMQ才会处理无法路由的消息；为false，RabbitMQ 将直接丢弃无法路由的消息
        channel.basicPublish("exchange.order.restaurant", "key.order",true, null, messageToSend.getBytes());
    }
}
