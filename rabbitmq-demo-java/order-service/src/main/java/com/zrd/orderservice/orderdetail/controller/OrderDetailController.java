package com.zrd.orderservice.orderdetail.controller;

import com.zrd.orderservice.orderdetail.service.OrderDetailService;
import com.zrd.orderservice.orderdetail.param.OrderDetailQueryParam;
import com.zrd.orderservice.orderdetail.vo.OrderDetailQueryVo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * @Description 订单 前端控制器
 * @Author ZRD
 * @Date 2023/03/12
 **/
@Slf4j
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController{

    @Autowired
    private OrderDetailService orderDetailService;


    /**
     * 添加 订单 对象
     */
    @PostMapping("/add")
    public Object addOrderDetail( @RequestBody OrderDetailQueryVo orderDetailQueryVo) throws Exception {
        Object id = orderDetailService.addOrderDetail(orderDetailQueryVo);
        return id;
    }

    /**
     * 修改 订单 对象
     */
    @PutMapping("/update")
    public Boolean updateOrderDetail( @RequestBody OrderDetailQueryVo orderDetailQueryVo) throws Exception {
        boolean flag = orderDetailService.updateOrderDetail(orderDetailQueryVo);
        return flag;
    }

    /**
     * 删除 订单 对象
     */
    @DeleteMapping("/delete")
    public Boolean deleteOrderDetail( @RequestParam(value = "ids") String ids) throws Exception {
        boolean flag = orderDetailService.deleteOrderDetail(ids);
        return flag;
    }

    /**
     * 获取 订单 对象详情
     */
    @GetMapping("/info/{id}")
    public OrderDetailQueryVo getOrderDetail(@PathVariable("id") String id) throws Exception {
        OrderDetailQueryVo orderDetailQueryVo = orderDetailService.getOrderDetailById(id);
        return orderDetailQueryVo;
    }

    
    /**
     * 获取 订单 列表
     */
    @PostMapping("/list")
    public List<OrderDetailQueryVo> getOrderDetailList( @RequestBody OrderDetailQueryParam orderDetailQueryParam) throws Exception {
        List<OrderDetailQueryVo> paging = orderDetailService.getOrderDetailList(orderDetailQueryParam);
        return paging;
    }


}