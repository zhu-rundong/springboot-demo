package com.zrd.deliverymanservice.deliveryman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrd.deliverymanservice.deliveryman.entity.DeliverymanEntity;
import com.zrd.deliverymanservice.deliveryman.param.DeliverymanQueryParam;
import com.zrd.deliverymanservice.deliveryman.vo.DeliverymanQueryVo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * @Description 骑手 Mapper 接口
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Mapper
public interface DeliverymanMapper extends BaseMapper<DeliverymanEntity> {

    /**
     * 根据ID获取 骑手 对象
     *
     * @param id
     * @return
     */
    DeliverymanQueryVo getDeliverymanById(Serializable id);

    /**
     * 获取 骑手 对象列表
     *
     * @param deliverymanQueryParam
     * @return
     */
    List<DeliverymanQueryVo> getDeliverymanList(@Param("param") DeliverymanQueryParam deliverymanQueryParam);

}