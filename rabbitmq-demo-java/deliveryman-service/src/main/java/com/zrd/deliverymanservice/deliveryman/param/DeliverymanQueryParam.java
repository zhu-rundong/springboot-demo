package com.zrd.deliverymanservice.deliveryman.param;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Description 骑手 查询参数对象
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Data
@Accessors(chain = true)
public class DeliverymanQueryParam implements Serializable {


    /**
     * 状态(1：有效，0：无效)
     */
    @Size(max = 1, message = "状态(1：有效，0：无效)长度不能超过 1 ！")
    private String status;

}
