package com.zrd.settlementservice.settlement.dto;

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
    //订单id
    private Long orderId;
    //订单状态
    private String orderStatus;
    //价格
    private BigDecimal price;
    //骑手id
    private Long deliverymanId;
    //商品id
    private Long productId;
    //用户id
    private Long accountId;
    //结算id
    private Long settlementId;
    //积分奖励id
    private Integer rewardId;
    //积分奖励数量
    private BigDecimal rewardAmount;
    //确认
    private Boolean confirmed;
}
