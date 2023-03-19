package com.zrd.orderservice.orderdetail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.zrd.orderservice.orderdetail.dto.OrderMessageDTO;
import com.zrd.orderservice.orderdetail.entity.OrderDetailEntity;
import com.zrd.orderservice.orderdetail.enums.OrderStatus;
import com.zrd.orderservice.orderdetail.mapper.OrderDetailMapper;
import com.zrd.orderservice.orderdetail.service.OrderDetailService;
import com.zrd.orderservice.orderdetail.param.OrderDetailQueryParam;
import com.zrd.orderservice.orderdetail.vo.OrderDetailQueryVo;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Description 订单 服务实现类
 * @Author ZRD
 * @Date 2023/03/12
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetailEntity> implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Object addOrderDetail(OrderDetailQueryVo orderDetailQueryVo) throws Exception {
        log.info("CreateOrder:OrderCreateVO:{}", orderDetailQueryVo);
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        BeanUtils.copyProperties(orderDetailQueryVo, orderDetailEntity);
        super.save(orderDetailEntity);
        Long detailEntityId = orderDetailEntity.getId();
        if(Objects.nonNull(detailEntityId)){
            OrderMessageDTO orderMessageDTO = new OrderMessageDTO();
            orderMessageDTO.setOrderId(detailEntityId);
            orderMessageDTO.setProductId(orderDetailQueryVo.getProductId());
            orderMessageDTO.setAccountId(orderDetailQueryVo.getAccountId());
            //当前订单状态为创建中
            orderMessageDTO.setOrderStatus(OrderStatus.ORDER_CREATING.getStatus());
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("192.168.78.100");
            try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
                String messageToSend = objectMapper.writeValueAsString(orderMessageDTO);
                //像商家微服务发送消息，确认商品信息
                channel.basicPublish(
                        "exchange.order.restaurant",
                        "key.restaurant",
                        null,
                        messageToSend.getBytes());
            }
        }
        return detailEntityId;
    }


    @Override
    public boolean updateOrderDetail(OrderDetailQueryVo orderDetailQueryVo) throws Exception {
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        BeanUtils.copyProperties(orderDetailQueryVo, orderDetailEntity);
        return super.updateById(orderDetailEntity);
    }

    @Override
    public boolean deleteOrderDetail(String ids) throws Exception {
        String[] idArray = ids.split(",");
        return super.removeByIds(Arrays.asList(idArray));
    }

    @Override
    public boolean deleteOrderDetail(OrderDetailQueryParam orderDetailQueryParam) throws Exception {
        LambdaQueryWrapper<OrderDetailEntity> lambdaQueryWrapper = new LambdaQueryWrapper();

        return super.remove(lambdaQueryWrapper);
    }

    @Override
    public OrderDetailQueryVo getOrderDetailById(Serializable id) throws Exception {
		//OrderDetailQueryVo orderDetailQueryVo = orderDetailMapper.getOrderDetailById(id);

        OrderDetailEntity orderDetailEntity = super.getById(id);
        OrderDetailQueryVo orderDetailQueryVo = new OrderDetailQueryVo();
        BeanUtils.copyProperties(orderDetailEntity, orderDetailQueryVo);
        return orderDetailQueryVo;
    }


    @Override
    public List<OrderDetailQueryVo> getOrderDetailList(OrderDetailQueryParam orderDetailQueryParam) throws Exception {
        List<OrderDetailQueryVo> orderDetailQueryVoList = orderDetailMapper.getOrderDetailList(orderDetailQueryParam);
        return orderDetailQueryVoList;
    }


}