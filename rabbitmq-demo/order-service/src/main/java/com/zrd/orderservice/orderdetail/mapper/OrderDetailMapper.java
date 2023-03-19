package com.zrd.orderservice.orderdetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrd.orderservice.orderdetail.entity.OrderDetailEntity;
import com.zrd.orderservice.orderdetail.param.OrderDetailQueryParam;
import com.zrd.orderservice.orderdetail.vo.OrderDetailQueryVo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * @Description 订单 Mapper 接口
 * @Author ZRD
 * @Date 2023/03/12
 **/
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetailEntity> {

    /**
     * 根据ID获取 订单 对象
     *
     * @param id
     * @return
     */
    OrderDetailQueryVo getOrderDetailById(Serializable id);

    /**
     * 获取 订单 对象列表
     *
     * @param orderDetailQueryParam
     * @return
     */
    List<OrderDetailQueryVo> getOrderDetailList(@Param("param") OrderDetailQueryParam orderDetailQueryParam);

}