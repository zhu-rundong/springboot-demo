package com.zrd.restaurantservice.product.controller;

import com.zrd.restaurantservice.product.param.ProductQueryParam;
import com.zrd.restaurantservice.product.service.ProductService;
import com.zrd.restaurantservice.product.vo.ProductQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description 产品 前端控制器
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController{

    @Autowired
    private ProductService productService;


    /**
     * 添加 产品 对象
     */
    @PostMapping("/add")
    public Object addProduct( @RequestBody ProductQueryVo productQueryVo) throws Exception {
        Object id = productService.addProduct(productQueryVo);
        return id;
    }

    /**
     * 修改 产品 对象
     */
    @PutMapping("/update")
    public Boolean updateProduct( @RequestBody ProductQueryVo productQueryVo) throws Exception {
        boolean flag = productService.updateProduct(productQueryVo);
        return flag;
    }

    /**
     * 删除 产品 对象
     */
    @DeleteMapping("/delete")
    public Boolean deleteProduct( @RequestParam(value = "ids") String ids) throws Exception {
        boolean flag = productService.deleteProduct(ids);
        return flag;
    }

    /**
     * 获取 产品 对象详情
     */
    @GetMapping("/info/{id}")
    public ProductQueryVo getProduct(@PathVariable("id") String id) throws Exception {
        ProductQueryVo productQueryVo = productService.getProductById(id);
        return productQueryVo;
    }

    
    /**
     * 获取 产品 列表
     */
    @PostMapping("/list")
    public List<ProductQueryVo> getProductList( @RequestBody ProductQueryParam productQueryParam) throws Exception {
        List<ProductQueryVo> paging = productService.getProductList(productQueryParam);
        return paging;
    }


}