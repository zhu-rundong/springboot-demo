package com.zrd.restaurantservice;

import com.zrd.restaurantservice.product.service.ProductService;
import com.zrd.restaurantservice.product.vo.ProductQueryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestaurantServiceApplicationTests {
    @Autowired
    private ProductService productService;
    @Test
    void contextLoads() {
        try {
            ProductQueryVo productById = productService.getProductById(1024L);
            System.out.println(productById);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
