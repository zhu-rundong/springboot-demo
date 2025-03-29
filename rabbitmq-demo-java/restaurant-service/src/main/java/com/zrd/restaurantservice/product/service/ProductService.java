package com.zrd.restaurantservice.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrd.restaurantservice.product.entity.ProductEntity;
import com.zrd.restaurantservice.product.param.ProductQueryParam;
import com.zrd.restaurantservice.product.vo.ProductQueryVo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 产品 服务类 接口
 * @Author ZRD
 * @Date 2023/03/19
 **/
public interface ProductService extends IService<ProductEntity> {

    /**
     * 保存 产品
     *
     * @param productQueryVo
     * @return
     * @throws Exception
     */
    Object addProduct(ProductQueryVo productQueryVo) throws Exception;

    /**
     * 修改 产品
     *
     * @param productQueryVo
     * @return
     * @throws Exception
     */
    boolean updateProduct(ProductQueryVo productQueryVo) throws Exception;

    /**
     * 删除 产品
     *
     * @param ids
     * @return
     * @throws Exception
     */
    boolean deleteProduct(String ids) throws Exception;
	
    /**
     * 根据条件删除 产品
     *
     * @param productQueryParam
     * @return
     * @throws Exception
     */
    boolean deleteProduct(ProductQueryParam productQueryParam) throws Exception;

    /**
     * 根据ID获取 产品 对象
     *
     * @param id
     * @return
     * @throws Exception
     */
     ProductQueryVo getProductById(Serializable id) throws Exception;


    /**
     * 获取 产品 对象列表
     *
     * @param productQueryParam
     * @return
     * @throws Exception
     */
     List<ProductQueryVo> getProductList(ProductQueryParam productQueryParam) throws Exception;

}