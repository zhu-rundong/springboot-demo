package com.zrd.orderservice;

import com.zrd.orderservice.orderdetail.enums.OrderStatus;
import com.zrd.orderservice.orderdetail.service.OrderDetailMessageService;
import com.zrd.orderservice.orderdetail.service.OrderDetailService;
import com.zrd.orderservice.orderdetail.vo.OrderDetailQueryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;

@SpringBootTest
class OrderServiceSpringBootApplicationTests {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderDetailMessageService orderDetailMessageService;


    /**
     * @description 用户下单
     * @author ZRD
     * @date 2023/3/12
     */
    @Test
    void testAddOrderDetail() {
        try {
            OrderDetailQueryVo orderDetailQueryVo = new OrderDetailQueryVo();
            orderDetailQueryVo.setAccountId(9527L);
            orderDetailQueryVo.setProductId(1024L);
            orderDetailQueryVo.setOrderAddress("9527用户108商品的订单地址");
            orderDetailQueryVo.setOrderStatus(OrderStatus.ORDER_CREATING.getStatus());
            orderDetailQueryVo.setDate(new Timestamp(System.currentTimeMillis()));
            orderDetailService.addOrderDetail(orderDetailQueryVo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
