package com.zrd.orderservice.orderdetail.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description 订单 前端控制器
 * @Author ZRD
 * @Date 2023/03/12
 **/
@Data
@Accessors(chain = true)
@TableName("t_order_detail")
public class OrderDetailEntity implements Serializable {

    /**
     * 订单id
     */
    @Size(max = 20, message = "订单id长度不能超过 20 ！")
    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    @Size(max = 20, message = "用户id长度不能超过 20 ！")
    @TableField(value = "account_id")
    private Long accountId;

    /**
     * 产品id
     */
    @Size(max = 20, message = "产品id长度不能超过 20 ！")
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 骑手id
     */
    @Size(max = 20, message = "骑手id长度不能超过 20 ！")
    @TableField(value = "deliveryman_id")
    private Long deliverymanId;

    /**
     * 结算id
     */
    @Size(max = 20, message = "结算id长度不能超过 20 ！")
    @TableField(value = "settlement_id")
    private Long settlementId;

    /**
     * 积分奖励id
     */
    @Size(max = 20, message = "积分奖励id长度不能超过 20 ！")
    @TableField(value = "reward_id")
    private Long rewardId;

    /**
     * 订单状态
     */
    @Size(max = 1, message = "订单状态，1：订单创建中，2：餐厅确认，3：骑手确认，4：已结算，5：订单创建成功，6：订单创建失败，长度不能超过 1 ！")
    @TableField(value = "order_status")
    private String orderStatus;

    /**
     * 订单地址
     */
    @Size(max = 100, message = "订单地址长度不能超过 100 ！")
    @TableField(value = "order_address")
    private String orderAddress;

    /**
     * 订单价格
     */
    @TableField(value = "order_price")
    private BigDecimal orderPrice;

    /**
     * 时间
     */
    @TableField(value = "date")
    private Timestamp date;

}
