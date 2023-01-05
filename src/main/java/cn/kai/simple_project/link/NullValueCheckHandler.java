package cn.kai.simple_project.link;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 空值校验处理器
 * Author: chenKai
 * Date: 2023/1/5
 */
@Component
@Slf4j
public class NullValueCheckHandler extends AbstractCheckHandler{
    @Override
    public JsonData handler(ProductVO product) {
        log.info("空值校验 Handler开始=======");

        //降级：如果配置了降级，则跳过此处理器，执行下一个处理器
        if (super.config.getDown()){
            log.info("空值校验 Handler已降级，跳过空值校验，执行下一个处理器");
            return super.next(product);
        }
        //参数必填校验
        if (Objects.isNull(product)){
            return JsonData.buildError("产品对象为空");
        }
        log.info("空值校验结束，通过=======");
        return super.next(product);
    }
}
