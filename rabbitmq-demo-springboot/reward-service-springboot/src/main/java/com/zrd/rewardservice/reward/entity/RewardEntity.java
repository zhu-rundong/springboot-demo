package com.zrd.rewardservice.reward.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Description  前端控制器
 * @Author ZRD
 * @Date 2025/03/16
 **/
@Data
@Accessors(chain = true)
@TableName("t_reward")
public class RewardEntity implements Serializable {

    /**
     * 奖励id
     */
    @Size(max = 20, message = "奖励id长度不能超过 20 ！")
    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单id
     */
    @Size(max = 20, message = "订单id长度不能超过 20 ！")
    @TableField(value = "order_id")
    private Long orderId;

    /**
     * 积分量
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 状态
     */
    @Size(max = 36, message = "状态长度不能超过 36 ！")
    @TableField(value = "status")
    private String status;

    /**
     * 时间
     */
    @TableField(value = "date")
    private Timestamp date;

}
