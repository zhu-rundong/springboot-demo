package com.zrd.deliverymanservice.rabbitmessge.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName CommonStatus
 * @Description 通用状态枚举
 * @Author ZRD
 * @Date 2023/3/12
 **/
public enum CommonStatus {
    STATUS_YES("1","是"),
    STATUS_ON("0","否"),
    ;
    private String status;

    private String desc;


    public static CommonStatus getStatus(String status){
        if (StringUtils.isEmpty(status)){
            return null;
        }
        for (CommonStatus enums : CommonStatus.values()) {
            if (enums.getStatus().equals(status)) {
                return enums;
            }
        }
        return null;
    }

    CommonStatus(String status, String desc) {
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
