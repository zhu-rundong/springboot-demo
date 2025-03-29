package com.zrd.deliverymanservice.deliveryman.controller;

import com.zrd.deliverymanservice.deliveryman.service.DeliverymanService;
import com.zrd.deliverymanservice.deliveryman.param.DeliverymanQueryParam;
import com.zrd.deliverymanservice.deliveryman.vo.DeliverymanQueryVo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * @Description 骑手 前端控制器
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Slf4j
@RestController
@RequestMapping("/deliveryman")
public class DeliverymanController{

    @Autowired
    private DeliverymanService deliverymanService;


    /**
     * 添加 骑手 对象
     */
    @PostMapping("/add")
    public Object addDeliveryman( @RequestBody DeliverymanQueryVo deliverymanQueryVo) throws Exception {
        Object id = deliverymanService.addDeliveryman(deliverymanQueryVo);
        return id;
    }

    /**
     * 修改 骑手 对象
     */
    @PutMapping("/update")
    public Boolean updateDeliveryman( @RequestBody DeliverymanQueryVo deliverymanQueryVo) throws Exception {
        boolean flag = deliverymanService.updateDeliveryman(deliverymanQueryVo);
        return flag;
    }

    /**
     * 删除 骑手 对象
     */
    @DeleteMapping("/delete")
    public Boolean deleteDeliveryman( @RequestParam(value = "ids") String ids) throws Exception {
        boolean flag = deliverymanService.deleteDeliveryman(ids);
        return flag;
    }

    /**
     * 获取 骑手 对象详情
     */
    @GetMapping("/info/{id}")
    public DeliverymanQueryVo getDeliveryman(@PathVariable("id") String id) throws Exception {
        DeliverymanQueryVo deliverymanQueryVo = deliverymanService.getDeliverymanById(id);
        return deliverymanQueryVo;
    }

    
    /**
     * 获取 骑手 列表
     */
    @PostMapping("/list")
    public List<DeliverymanQueryVo> getDeliverymanList( @RequestBody DeliverymanQueryParam deliverymanQueryParam) throws Exception {
        List<DeliverymanQueryVo> paging = deliverymanService.getDeliverymanList(deliverymanQueryParam);
        return paging;
    }


}