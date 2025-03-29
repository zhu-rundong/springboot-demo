package com.zrd.restaurantservice.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrd.restaurantservice.product.entity.ProductEntity;
import com.zrd.restaurantservice.product.mapper.ProductMapper;
import com.zrd.restaurantservice.product.param.ProductQueryParam;
import com.zrd.restaurantservice.product.service.ProductService;
import com.zrd.restaurantservice.product.vo.ProductQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 产品 服务实现类
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductService {

    @Resource
    private ProductMapper productMapper;


    @Override
    public Object addProduct(ProductQueryVo productQueryVo) throws Exception {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productQueryVo, productEntity);
        super.save(productEntity);
        return productEntity.getId();
    }


    @Override
    public boolean updateProduct(ProductQueryVo productQueryVo) throws Exception {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productQueryVo, productEntity);
        return super.updateById(productEntity);
    }

    @Override
    public boolean deleteProduct(String ids) throws Exception {
        String[] idArray = ids.split(",");
        return super.removeByIds(Arrays.asList(idArray));
    }

    @Override
    public boolean deleteProduct(ProductQueryParam productQueryParam) throws Exception {
        LambdaQueryWrapper<ProductEntity> lambdaQueryWrapper = new LambdaQueryWrapper();

        return super.remove(lambdaQueryWrapper);
    }

    @Override
    public ProductQueryVo getProductById(Serializable id) throws Exception {
        //ProductEntity productEntity = super.getById(id);
        //ProductQueryVo productQueryVo = new ProductQueryVo();
        //BeanUtils.copyProperties(productEntity, productQueryVo);
        return productMapper.getProductById(id);
    }


    @Override
    public List<ProductQueryVo> getProductList(ProductQueryParam productQueryParam) throws Exception {
        List<ProductQueryVo> productQueryVoList = productMapper.getProductList(productQueryParam);
        return productQueryVoList;
    }


}