package com.zrd.restaurantservice.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrd.restaurantservice.restaurant.entity.RestaurantEntity;
import com.zrd.restaurantservice.restaurant.mapper.RestaurantMapper;
import com.zrd.restaurantservice.restaurant.param.RestaurantQueryParam;
import com.zrd.restaurantservice.restaurant.service.RestaurantService;
import com.zrd.restaurantservice.restaurant.vo.RestaurantQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 餐厅 服务实现类
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RestaurantServiceImpl extends ServiceImpl<RestaurantMapper, RestaurantEntity> implements RestaurantService {

    @Resource
    private RestaurantMapper restaurantMapper;


    @Override
    public Object addRestaurant(RestaurantQueryVo restaurantQueryVo) throws Exception {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        BeanUtils.copyProperties(restaurantQueryVo, restaurantEntity);
        super.save(restaurantEntity);
        return restaurantEntity.getId();
    }


    @Override
    public boolean updateRestaurant(RestaurantQueryVo restaurantQueryVo) throws Exception {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        BeanUtils.copyProperties(restaurantQueryVo, restaurantEntity);
        return super.updateById(restaurantEntity);
    }

    @Override
    public boolean deleteRestaurant(String ids) throws Exception {
        String[] idArray = ids.split(",");
        return super.removeByIds(Arrays.asList(idArray));
    }

    @Override
    public boolean deleteRestaurant(RestaurantQueryParam restaurantQueryParam) throws Exception {
        LambdaQueryWrapper<RestaurantEntity> lambdaQueryWrapper = new LambdaQueryWrapper();

        return super.remove(lambdaQueryWrapper);
    }

    @Override
    public RestaurantQueryVo getRestaurantById(Serializable id) throws Exception {
		//RestaurantQueryVo restaurantQueryVo = restaurantMapper.getRestaurantById(id);

        RestaurantEntity restaurantEntity = super.getById(id);
        RestaurantQueryVo restaurantQueryVo = new RestaurantQueryVo();
        BeanUtils.copyProperties(restaurantEntity, restaurantQueryVo);
        return restaurantQueryVo;
    }


    @Override
    public List<RestaurantQueryVo> getRestaurantList(RestaurantQueryParam restaurantQueryParam) throws Exception {
        List<RestaurantQueryVo> restaurantQueryVoList = restaurantMapper.getRestaurantList(restaurantQueryParam);
        return restaurantQueryVoList;
    }


}