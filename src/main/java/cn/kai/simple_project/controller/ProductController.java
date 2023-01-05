package cn.kai.simple_project.controller;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.service.ProductService;
import cn.kai.simple_project.vo.ProductVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 产品控制类
 * Author: chenKai
 * Date: 2023/1/5
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping(value = "/createProduct")
    public JsonData createProduct(@RequestBody ProductVO product){
        return productService.createProduct(product);
    }


}
