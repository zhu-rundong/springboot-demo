package com.zrd.restaurantservice.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrd.restaurantservice.product.entity.ProductEntity;
import com.zrd.restaurantservice.product.param.ProductQueryParam;
import com.zrd.restaurantservice.product.vo.ProductQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 产品 Mapper 接口
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Mapper
public interface ProductMapper extends BaseMapper<ProductEntity> {

    /**
     * 根据ID获取 产品 对象
     *
     * @param id
     * @return
     */
    ProductQueryVo getProductById(Serializable id);

    /**
     * 获取 产品 对象列表
     *
     * @param productQueryParam
     * @return
     */
    List<ProductQueryVo> getProductList(@Param("param") ProductQueryParam productQueryParam);

}