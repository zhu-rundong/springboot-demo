package com.zrd.restaurantservice.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrd.restaurantservice.restaurant.entity.RestaurantEntity;
import com.zrd.restaurantservice.restaurant.param.RestaurantQueryParam;
import com.zrd.restaurantservice.restaurant.vo.RestaurantQueryVo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 餐厅 服务类 接口
 * @Author ZRD
 * @Date 2023/03/19
 **/
public interface RestaurantService extends IService<RestaurantEntity> {

    /**
     * 保存 餐厅
     *
     * @param restaurantQueryVo
     * @return
     * @throws Exception
     */
    Object addRestaurant(RestaurantQueryVo restaurantQueryVo) throws Exception;

    /**
     * 修改 餐厅
     *
     * @param restaurantQueryVo
     * @return
     * @throws Exception
     */
    boolean updateRestaurant(RestaurantQueryVo restaurantQueryVo) throws Exception;

    /**
     * 删除 餐厅
     *
     * @param ids
     * @return
     * @throws Exception
     */
    boolean deleteRestaurant(String ids) throws Exception;
	
    /**
     * 根据条件删除 餐厅
     *
     * @param restaurantQueryParam
     * @return
     * @throws Exception
     */
    boolean deleteRestaurant(RestaurantQueryParam restaurantQueryParam) throws Exception;

    /**
     * 根据ID获取 餐厅 对象
     *
     * @param id
     * @return
     * @throws Exception
     */
     RestaurantQueryVo getRestaurantById(Serializable id) throws Exception;


    /**
     * 获取 餐厅 对象列表
     *
     * @param restaurantQueryParam
     * @return
     * @throws Exception
     */
     List<RestaurantQueryVo> getRestaurantList(RestaurantQueryParam restaurantQueryParam) throws Exception;

}