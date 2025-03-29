package com.zrd.restaurantservice.restaurant.controller;

import com.zrd.restaurantservice.restaurant.param.RestaurantQueryParam;
import com.zrd.restaurantservice.restaurant.service.RestaurantService;
import com.zrd.restaurantservice.restaurant.vo.RestaurantQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description 餐厅 前端控制器
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Slf4j
@RestController
@RequestMapping("/restaurant")
public class RestaurantController{

    @Autowired
    private RestaurantService restaurantService;


    /**
     * 添加 餐厅 对象
     */
    @PostMapping("/add")
    public Object addRestaurant( @RequestBody RestaurantQueryVo restaurantQueryVo) throws Exception {
        Object id = restaurantService.addRestaurant(restaurantQueryVo);
        return id;
    }

    /**
     * 修改 餐厅 对象
     */
    @PutMapping("/update")
    public Boolean updateRestaurant( @RequestBody RestaurantQueryVo restaurantQueryVo) throws Exception {
        boolean flag = restaurantService.updateRestaurant(restaurantQueryVo);
        return flag;
    }

    /**
     * 删除 餐厅 对象
     */
    @DeleteMapping("/delete")
    public Boolean deleteRestaurant( @RequestParam(value = "ids") String ids) throws Exception {
        boolean flag = restaurantService.deleteRestaurant(ids);
        return flag;
    }

    /**
     * 获取 餐厅 对象详情
     */
    @GetMapping("/info/{id}")
    public RestaurantQueryVo getRestaurant(@PathVariable("id") String id) throws Exception {
        RestaurantQueryVo restaurantQueryVo = restaurantService.getRestaurantById(id);
        return restaurantQueryVo;
    }

    
    /**
     * 获取 餐厅 列表
     */
    @PostMapping("/list")
    public List<RestaurantQueryVo> getRestaurantList( @RequestBody RestaurantQueryParam restaurantQueryParam) throws Exception {
        List<RestaurantQueryVo> paging = restaurantService.getRestaurantList(restaurantQueryParam);
        return paging;
    }


}