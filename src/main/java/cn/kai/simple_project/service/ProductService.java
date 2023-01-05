package cn.kai.simple_project.service;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Author: chenKai
 * Date: 2023/1/5
 */
public interface ProductService {

    JsonData createProduct(ProductVO product);
}
