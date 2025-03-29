package com.zrd.restaurantservice.restaurant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Description 餐厅 前端控制器
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Data
@Accessors(chain = true)
@TableName("t_restaurant")
public class RestaurantEntity implements Serializable {

    /**
     * 餐厅id
     */
    @Size(max = 20, message = "餐厅id长度不能超过 20 ！")
    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 餐厅名称
     */
    @Size(max = 50, message = "餐厅名称长度不能超过 50 ！")
    @TableField(value = "name")
    private String name;

    /**
     * 餐厅地址
     */
    @Size(max = 100, message = "餐厅地址长度不能超过 100 ！")
    @TableField(value = "address")
    private String address;

    /**
     * 餐厅状态
     */
    @Size(max = 1, message = "餐厅状态长度不能超过 1 ！")
    @TableField(value = "status")
    private String status;

    /**
     * 时间
     */
    @TableField(value = "date")
    private Timestamp date;

}
