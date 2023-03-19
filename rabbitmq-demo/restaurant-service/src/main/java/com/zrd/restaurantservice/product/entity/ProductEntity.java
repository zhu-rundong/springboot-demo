package com.zrd.restaurantservice.product.entity;

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
 * @Description 产品 前端控制器
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Data
@Accessors(chain = true)
@TableName("t_product")
public class ProductEntity implements Serializable {

    /**
     * 产品id
     */
    @Size(max = 20, message = "产品id长度不能超过 20 ！")
    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 地址
     */
    @Size(max = 20, message = "地址长度不能超过 20 ！")
    @TableField(value = "restaurant_id")
    private Long restaurantId;

    /**
     * 产品名称
     */
    @Size(max = 100, message = "产品名称长度不能超过 100 ！")
    @TableField(value = "product_name")
    private String productName;

    /**
     * 产品单价
     */
    @TableField(value = "product_price")
    private BigDecimal productPrice;

    /**
     * 产品状态
     */
    @Size(max = 1, message = "产品状态长度不能超过 1 ！")
    @TableField(value = "product_status")
    private String productStatus;

    /**
     * 时间
     */
    @TableField(value = "date")
    private Timestamp date;

}
