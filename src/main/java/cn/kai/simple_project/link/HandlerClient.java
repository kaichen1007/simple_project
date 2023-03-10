package cn.kai.simple_project.link;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.link.flowLink.AbstractFlowHandler;
import cn.kai.simple_project.link.productLink.AbstractCheckHandler;
import cn.kai.simple_project.vo.ApplyUserVO;
import cn.kai.simple_project.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 链路客户端 负责发起整个处理链路的执行
 * Author: chenKai
 * Date: 2023/1/5
 */
@Service
@Slf4j
public class HandlerClient {

    /**
     * 处理器
     * @param handler
     * @param product
     * @return
     */
    public JsonData executeChain(AbstractCheckHandler handler, ProductVO product){
        JsonData handlerResult = handler.handler(product);

        if (handlerResult.getCode() == -1){
            log.error("HandlerClient 责任链执行失败返回:{}",handlerResult.getMsg());
            return handlerResult;
        }
        return JsonData.buildSuccess();
    }


    /**
     * 审批流程处理器
     * @param handler
     * @param applyUser
     * @return
     */
    public JsonData executeFlowChain(AbstractFlowHandler handler, ApplyUserVO applyUser){
        JsonData handlerResult = handler.handler(applyUser, handler.getConfig());

        if (handlerResult.getCode() == -1){
            log.error("HandlerClient 审批责任链执行失败返回:{}",handlerResult.getMsg());
            return handlerResult;
        }
        return JsonData.buildSuccess();
    }


}
