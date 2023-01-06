package cn.kai.simple_project.config.link;

import cn.kai.simple_project.common.utils.StringUtils;
import cn.kai.simple_project.link.flowLink.AbstractFlowHandler;
import cn.kai.simple_project.link.flowLink.FLowHandlerConfig;
import cn.kai.simple_project.link.productLink.AbstractCheckHandler;
import cn.kai.simple_project.link.productLink.CheckHandlerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Resource
    private Map<String, AbstractFlowHandler> flowHandlerMap;

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

    public FLowHandlerConfig getFlowHandlerConfig(){
        return FLowHandlerConfig.builder()
                .handler("threeLevelFlowHandler")
                .approveUser("李总(第三级审批)")
                    .minMoney(new BigDecimal("1"))
                    .maxMoney(new BigDecimal("3000"))
                .down(Boolean.FALSE)
                .next(
                        FLowHandlerConfig.builder()
                          .handler("twoLevelFlowHandler")
                          .approveUser("王总(第二级审批)")
                            .minMoney(new BigDecimal("3001"))
                            .maxMoney(new BigDecimal("6000"))
                          .down(Boolean.FALSE)
                          .next(
                                  FLowHandlerConfig.builder()
                                  .handler("oneLevelFlowHandler")
                                  .approveUser("陈总(第一级审批)")
                                          .minMoney(new BigDecimal("6001"))
                                          .maxMoney(new BigDecimal("10000"))
                                  .down(Boolean.FALSE)
                                  .build()
                          ).build()
                ).build();
    }

    public AbstractFlowHandler getFlowHandler(FLowHandlerConfig config){
        if (Objects.isNull(config)){
            log.error("获取处理器为空========");
            return null;
        }
        if (StringUtils.isEmptyAll(config.getHandler())){
            log.error("获取配置错误======:{}",config);
            return null;
        }
        AbstractFlowHandler handler = flowHandlerMap.get(config.getHandler());
        if (Objects.isNull(handler)){
            log.error("获取了不存在的配置=====:{}",config);
            return null;
        }

        //处理器设置配置Config
        handler.setConfig(config);

        //进行迭代处理器
        handler.setNextHandler(this.getFlowHandler(config.getNext()));

        return handler;
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
