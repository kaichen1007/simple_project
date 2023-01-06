package cn.kai.simple_project.link.flowLink;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.vo.ApplyUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 一等级审批人
 * Author: chenKai
 * Date: 2023/1/6
 */
@Component
@Slf4j
public class OneLevelFlowHandler extends AbstractFlowHandler{

    @Override
    public JsonData handler(ApplyUserVO applyUser, FLowHandlerConfig config) {
        if (super.config.getDown()){
            log.info("{} Handler已降级，跳过空值校验，执行下一个处理器",config.getApproveUser());
            return super.nextFLow(applyUser);
        }
        log.info("{},审批开始,当前审批金额:{}",config.getApproveUser(),applyUser.getApplyAmount());

        if (applyUser.getApplyAmount().compareTo(config.getMaxMoney()) > 0){
            log.info("当前:{},大于最大审批人:{}审批金额:{},退回",applyUser,config.getApproveUser(),config.getMaxMoney());
            return JsonData.buildError("超过最大审批金额");
        }
        log.info("{},审批通过",config.getApproveUser());
        return super.nextFLow(applyUser);
    }
}
