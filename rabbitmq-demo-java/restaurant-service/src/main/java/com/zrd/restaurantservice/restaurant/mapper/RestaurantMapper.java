package com.zrd.restaurantservice.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrd.restaurantservice.restaurant.entity.RestaurantEntity;
import com.zrd.restaurantservice.restaurant.param.RestaurantQueryParam;
import com.zrd.restaurantservice.restaurant.vo.RestaurantQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 餐厅 Mapper 接口
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Mapper
public interface RestaurantMapper extends BaseMapper<RestaurantEntity> {

    /**
     * 根据ID获取 餐厅 对象
     *
     * @param id
     * @return
     */
    RestaurantQueryVo getRestaurantById(Serializable id);

    /**
     * 获取 餐厅 对象列表
     *
     * @param restaurantQueryParam
     * @return
     */
    List<RestaurantQueryVo> getRestaurantList(@Param("param") RestaurantQueryParam restaurantQueryParam);

}