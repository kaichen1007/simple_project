package cn.kai.simple_project.link;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 库存校验处理器
 * Author: chenKai
 * Date: 2023/1/5
 */
@Component
@Slf4j
public class StockCheckHandler extends AbstractCheckHandler{
    @Override
    public JsonData handler(ProductVO product) {
        log.info("库存校验 Handler开始======");

        //降级：如果配置了降级，则跳过此处理器，执行下一个处理器
        if (super.config.getDown()){
            log.info("库存校验 Handler已降级，跳过空值校验，执行下一个处理器");
            return super.next(product);
        }

        if (product.getStock() < 0){
            return JsonData.buildError("库存不可小于0!");
        }


        log.info("库存校验结束 通过======");
        return super.next(product);
    }
}
