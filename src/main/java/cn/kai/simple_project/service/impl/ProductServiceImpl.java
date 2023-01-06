package cn.kai.simple_project.service.impl;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.config.link.LinkConfig;
import cn.kai.simple_project.link.productLink.AbstractCheckHandler;
import cn.kai.simple_project.link.HandlerClient;
import cn.kai.simple_project.link.productLink.CheckHandlerConfig;
import cn.kai.simple_project.service.ProductService;
import cn.kai.simple_project.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author: chenKai
 * Date: 2023/1/5
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Resource
    private LinkConfig linkConfig;

    @Resource
    private HandlerClient handlerClient;


    @Override
    public JsonData createProduct(ProductVO product) {
        CheckHandlerConfig productHandlerConfig = linkConfig.getProductHandlerConfig();
        AbstractCheckHandler handler = linkConfig.getHandler(linkConfig.getProductHandlerConfig());
        return handlerClient.executeChain(handler,product);

    }
}
