package com.zrd.deliverymanservice.deliveryman.entity;

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
 * @Description 骑手 前端控制器
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Data
@Accessors(chain = true)
@TableName("t_deliveryman")
public class DeliverymanEntity implements Serializable {

    /**
     * 骑手id
     */
    @Size(max = 20, message = "骑手id长度不能超过 20 ！")
    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    @Size(max = 36, message = "名称长度不能超过 36 ！")
    @TableField(value = "name")
    private String name;

    /**
     * 状态
     */
    @Size(max = 1, message = "状态长度不能超过 1 ！")
    @TableField(value = "status")
    private String status;

    /**
     * 时间
     */
    @TableField(value = "date")
    private Timestamp date;

}
