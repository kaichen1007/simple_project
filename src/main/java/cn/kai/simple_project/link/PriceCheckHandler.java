package cn.kai.simple_project.link;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 价格校验处理器
 * Author: chenKai
 * Date: 2023/1/5
 */
@Component
@Slf4j
public class PriceCheckHandler extends AbstractCheckHandler{
    @Override
    public JsonData handler(ProductVO product) {
        log.info("价格校验 Handler开始======");

        //非法参数校验
        boolean flag = product.getPrice().compareTo(BigDecimal.ZERO) <= 0;
        if (flag){
            return JsonData.buildError("价格不可为0!");
        }
        if (product.getPrice().compareTo(new BigDecimal("10000000")) > 0){
            return JsonData.buildError("价格过大!");
        }
        log.info("价格校验结束 通过======");
        return super.next(product);
    }
}

