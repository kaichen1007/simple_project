package cn.kai.simple_project.service.impl;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.config.link.LinkConfig;
import cn.kai.simple_project.link.HandlerClient;
import cn.kai.simple_project.link.flowLink.AbstractFlowHandler;
import cn.kai.simple_project.service.FlowService;
import cn.kai.simple_project.vo.ApplyUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author: chenKai
 * Date: 2023/1/6
 */
@Service
@Slf4j
public class FlowServiceImpl implements FlowService {
    @Resource
    private HandlerClient handlerClient;
    @Resource
    private LinkConfig linkConfig;
    @Override
    public JsonData flowApply(ApplyUserVO applyUserVO) {
        AbstractFlowHandler flowHandler = linkConfig.getFlowHandler(linkConfig.getFlowHandlerConfig());
        return handlerClient.executeFlowChain(flowHandler,applyUserVO);
    }
}
