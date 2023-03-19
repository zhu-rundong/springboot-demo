package com.zrd.restaurantservice.rabbitmessge.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName OrderStatus
 * @Description 订单状态枚举
 * @Author ZRD
 * @Date 2023/3/12
 **/
public enum OrderStatus {
    ORDER_CREATING("1","订单创建中"),
    RESTAURANT_CONFIRMED("2","商家确认"),
    DELIVERYMAN_CONFIRMED("3","骑手确认"),
    SETTLEMENT_CONFIRMED("4","支付确认"),
    ORDER_CREATE_SUCCESS("5","订单创建成功"),
    ORDER_CREATE_FAILED("6","订单创建失败")
    ;
    private String status;

    private String desc;


    public static OrderStatus getStatus(String status){
        if (StringUtils.isEmpty(status)){
            return null;
        }
        for (OrderStatus enums : OrderStatus.values()) {
            if (enums.getStatus().equals(status)) {
                return enums;
            }
        }
        return null;
    }

    OrderStatus(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
