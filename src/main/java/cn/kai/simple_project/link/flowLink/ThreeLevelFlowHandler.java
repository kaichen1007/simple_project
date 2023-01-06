package cn.kai.simple_project.link.flowLink;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.vo.ApplyUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Author: chenKai
 * Date: 2023/1/6
 */
@Component
@Slf4j
public class ThreeLevelFlowHandler extends AbstractFlowHandler{
    @Override
    public JsonData handler(ApplyUserVO applyUser, FLowHandlerConfig config) {
        if (super.config.getDown()){
            log.info("{} Handler已降级，跳过空值校验，执行下一个处理器",config.getApproveUser());
            return super.nextFLow(applyUser);
        }
        log.info("{},审批开始,当前审批金额:{}",config.getApproveUser(),applyUser.getApplyAmount());


        log.info("{},审批通过",config.getApproveUser());
        return super.nextFLow(applyUser);
    }
}
