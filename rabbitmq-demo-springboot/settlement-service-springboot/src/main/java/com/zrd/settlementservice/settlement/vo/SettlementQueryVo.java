package com.zrd.settlementservice.settlement.vo;

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
public class SettlementQueryVo implements Serializable {

    /**
     * 结算id
     */
    @Size(max = 20, message = "结算id长度不能超过 20 ！")
    private Long id;

    /**
     * 订单id
     */
    @Size(max = 20, message = "订单id长度不能超过 20 ！")
    private Long orderId;

    /**
     * 交易id
     */
    @Size(max = 20, message = "交易id长度不能超过 20 ！")
    private Long transactionId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 状态
     */
    private String status;

    /**
     * 时间
     */
    private Timestamp date;

}