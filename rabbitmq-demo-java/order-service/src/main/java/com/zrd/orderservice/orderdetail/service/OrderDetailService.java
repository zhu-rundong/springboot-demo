package com.zrd.orderservice.orderdetail.service;

import com.zrd.orderservice.orderdetail.entity.OrderDetailEntity;
import com.zrd.orderservice.orderdetail.param.OrderDetailQueryParam;
import com.zrd.orderservice.orderdetail.vo.OrderDetailQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 订单 服务类 接口
 * @Author ZRD
 * @Date 2023/03/12
 **/
public interface OrderDetailService extends IService<OrderDetailEntity> {

    /**
     * 保存 订单
     *
     * @param orderDetailQueryVo
     * @return
     * @throws Exception
     */
    Object addOrderDetail(OrderDetailQueryVo orderDetailQueryVo) throws Exception;

    /**
     * 修改 订单
     *
     * @param orderDetailQueryVo
     * @return
     * @throws Exception
     */
    boolean updateOrderDetail(OrderDetailQueryVo orderDetailQueryVo) throws Exception;

    /**
     * 删除 订单
     *
     * @param ids
     * @return
     * @throws Exception
     */
    boolean deleteOrderDetail(String ids) throws Exception;
	
    /**
     * 根据条件删除 订单
     *
     * @param orderDetailQueryParam
     * @return
     * @throws Exception
     */
    boolean deleteOrderDetail(OrderDetailQueryParam orderDetailQueryParam) throws Exception;

    /**
     * 根据ID获取 订单 对象
     *
     * @param id
     * @return
     * @throws Exception
     */
     OrderDetailQueryVo getOrderDetailById(Serializable id) throws Exception;


    /**
     * 获取 订单 对象列表
     *
     * @param orderDetailQueryParam
     * @return
     * @throws Exception
     */
     List<OrderDetailQueryVo> getOrderDetailList(OrderDetailQueryParam orderDetailQueryParam) throws Exception;

}