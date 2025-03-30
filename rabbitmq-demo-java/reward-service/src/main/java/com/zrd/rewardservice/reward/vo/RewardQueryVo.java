package com.zrd.rewardservice.reward.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Description  查询结果对象
 * @Author ZRD
 * @Date 2025/03/16
 **/
@Data
@Accessors(chain = true)
public class RewardQueryVo implements Serializable {

    /**
     * 奖励id
     */
    @Size(max = 20, message = "奖励id长度不能超过 20 ！")
    private Long id;

    /**
     * 订单id
     */
    @Size(max = 20, message = "订单id长度不能超过 20 ！")
    private Long orderId;

    /**
     * 积分量
     */
    private BigDecimal amount;

    /**
     * 状态
     */
    @Size(max = 36, message = "状态长度不能超过 36 ！")
    private String status;

    /**
     * 时间
     */
    private Timestamp date;

}