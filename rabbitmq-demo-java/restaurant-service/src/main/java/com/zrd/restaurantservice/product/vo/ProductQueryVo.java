package com.zrd.restaurantservice.product.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Description 产品 查询结果对象
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Data
@Accessors(chain = true)
public class ProductQueryVo implements Serializable {

    /**
     * 产品id
     */
    @Size(max = 20, message = "产品id长度不能超过 20 ！")
    private Long id;

    /**
     * 地址
     */
    @Size(max = 20, message = "地址长度不能超过 20 ！")
    private Long restaurantId;

    /**
     * 产品名称
     */
    @Size(max = 100, message = "产品名称长度不能超过 100 ！")
    private String productName;

    /**
     * 产品单价
     */
    private BigDecimal productPrice;

    /**
     * 产品状态
     */
    @Size(max = 1, message = "产品状态(1：有效，0：无效)长度不能超过 1 ！")
    private String productStatus;

    /**
     * 时间
     */
    private Timestamp date;

}