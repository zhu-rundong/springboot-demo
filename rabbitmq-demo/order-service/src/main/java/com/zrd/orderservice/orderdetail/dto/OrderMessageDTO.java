package com.zrd.orderservice.orderdetail.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName OrderMessageDTO
 * @Description 订单消息实体
 * @Author ZRD
 * @Date 2023/3/12
 **/
@Data
public class OrderMessageDTO {
    private Long orderId;
    private String orderStatus;
    private BigDecimal price;
    private Long deliverymanId;
    private Long productId;
    private Long accountId;
    private Boolean confirmed;
}
