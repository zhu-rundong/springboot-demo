package com.zrd.deliverymanservice.rabbitmessge.dto;

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
    /**
     * 订单状态，订单状态，1：订单创建中，2：商家确认，3：骑手确认，4：订单创建成功，5：订单创建失败
     */
    private String orderStatus;
    private BigDecimal price;
    private Long deliverymanId;
    private Long productId;
    private Long accountId;
    private Long settlementId;
    private Long rewardId;
    private BigDecimal rewardAmount;
    private Boolean confirmed;
}
