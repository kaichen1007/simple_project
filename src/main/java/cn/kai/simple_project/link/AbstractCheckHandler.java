package cn.kai.simple_project.link;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.vo.ProductVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 处理器抽象类，抽象行为，子类共有属性、方法
 * Author: chenKai
 * Date: 2023/1/5
 */
@Component
public abstract class AbstractCheckHandler {

    /**
     * 当前处理器持有下一个处理器的引用
     */
    @Getter
    @Setter
    protected AbstractCheckHandler nextHandler;


    /**
     * 处理器配置
     */
    @Getter
    @Setter
    protected ProductCheckHandlerConfig config;

    /**
     * 处理器执行方法
     * @param product
     * @return
     */
    public abstract JsonData handler(ProductVO product);

    /**
     * 链路传递
     * @param product
     * @return
     */
    protected JsonData next(ProductVO product){
        //下一个链路没有处理器，直接返回
        if (Objects.isNull(nextHandler)){
            JsonData.buildSuccess();
        }
        //执行下一个处理器
        return nextHandler.handler(product);
    }


}
