package com.zrd.restaurantservice.restaurant.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Description 餐厅 查询结果对象
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Data
@Accessors(chain = true)
public class RestaurantQueryVo implements Serializable {

    /**
     * 餐厅id
     */
    @Size(max = 20, message = "餐厅id长度不能超过 20 ！")
    private Long id;

    /**
     * 结算id
     */
    @Size(max = 20, message = "结算id长度不能超过 20 ！")
    private Long settlementId;

    /**
     * 餐厅名称
     */
    @Size(max = 50, message = "餐厅名称长度不能超过 50 ！")
    private String name;

    /**
     * 餐厅地址
     */
    @Size(max = 100, message = "餐厅地址长度不能超过 100 ！")
    private String address;

    /**
     * 餐厅状态
     */
    @Size(max = 1, message = "餐厅状态(1：有效，0：无效)长度不能超过 1 ！")
    private String status;

    /**
     * 时间
     */
    private Timestamp date;

}