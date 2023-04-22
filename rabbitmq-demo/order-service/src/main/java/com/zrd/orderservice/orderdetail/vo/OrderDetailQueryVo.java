package com.zrd.orderservice.orderdetail.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Description 订单 查询结果对象
 * @Author ZRD
 * @Date 2023/03/12
 **/
@Data
@Accessors(chain = true)
public class OrderDetailQueryVo implements Serializable {

    /**
     * 订单id
     */
    @Size(max = 20, message = "订单id长度不能超过 20 ！")
    private Long id;

    /**
     * 用户id
     */
    @Size(max = 20, message = "用户id长度不能超过 20 ！")
    private Long accountId;

    /**
     * 产品id
     */
    @Size(max = 20, message = "产品id长度不能超过 20 ！")
    private Long productId;

    /**
     * 骑手id
     */
    @Size(max = 20, message = "骑手id长度不能超过 20 ！")
    private Long deliverymanId;


    /**
     * 订单状态，订单状态，1：订单创建中，2：商家确认，3：骑手确认，4：订单创建成功，5：订单创建失败
     */
    @Size(max = 1, message = "订单状态长度不能超过 1 ！")
    private String orderStatus;

    /**
     * 订单地址
     */
    @Size(max = 100, message = "订单地址长度不能超过 100 ！")
    private String orderAddress;

    /**
     * 订单价格
     */
    private BigDecimal orderPrice;

    /**
     * 时间
     */
    private Timestamp date;

}