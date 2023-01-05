package cn.kai.simple_project.config.link;

import cn.kai.simple_project.common.utils.StringUtils;
import cn.kai.simple_project.link.AbstractCheckHandler;
import cn.kai.simple_project.link.CheckHandlerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * 责任链配置
 * Author: chenKai
 * Date: 2023/1/5
 */
@Component
@Slf4j
public class LinkConfig {

    @Resource
    private Map<String, AbstractCheckHandler> handlerMap;

    /**
     * 创建责任链
     * 可在配置文件中配置
     * @return
     */
    public CheckHandlerConfig getProductHandlerConfig(){
        return CheckHandlerConfig.builder()
                .handler("nullValueCheckHandler")
                .down(Boolean.FALSE)
                .next(
                        CheckHandlerConfig.builder()
                        .handler("priceCheckHandler")
                        .down(Boolean.FALSE)
                        .next(
                                CheckHandlerConfig.builder()
                                .handler("stockCheckHandler")
                                .down(Boolean.FALSE)
                                .build()
                        ).build()
                ).build();
    }

    /**
     * 获取处理器
     * @param config
     * @return
     */
    public AbstractCheckHandler getHandler(CheckHandlerConfig config){
        if (Objects.isNull(config)){
            log.error("获取处理器为空========");
            return null;
        }
        if (StringUtils.isEmptyAll(config.getHandler())){
            log.error("获取配置错误======:{}",config);
            return null;
        }
        AbstractCheckHandler handler = handlerMap.get(config.getHandler());
        if (Objects.isNull(handler)){
            log.error("获取了不存在的配置=====:{}",config);
            return null;
        }

        //处理器设置配置Config
        handler.setConfig(config);

        //进行迭代处理器
        handler.setNextHandler(this.getHandler(config.getNext()));

        return handler;

    }


}
