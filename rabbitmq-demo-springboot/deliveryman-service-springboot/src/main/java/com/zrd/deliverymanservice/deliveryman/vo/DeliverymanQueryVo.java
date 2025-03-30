package com.zrd.deliverymanservice.deliveryman.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Description 骑手 查询结果对象
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Data
@Accessors(chain = true)
public class DeliverymanQueryVo implements Serializable {

    /**
     * 骑手id
     */
    @Size(max = 20, message = "骑手id长度不能超过 20 ！")
    private Long id;

    /**
     * 名称
     */
    @Size(max = 36, message = "名称长度不能超过 36 ！")
    private String name;

    /**
     * 状态(1：有效，0：无效)
     */
    @Size(max = 1, message = "状态(1：有效，0：无效)长度不能超过 1 ！")
    private String status;

    /**
     * 时间
     */
    private Timestamp date;

}