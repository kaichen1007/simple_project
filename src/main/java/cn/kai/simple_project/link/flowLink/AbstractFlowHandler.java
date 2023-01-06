package cn.kai.simple_project.link.flowLink;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.vo.ApplyUserVO;
import cn.kai.simple_project.vo.ProductVO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 审批流程处理器
 * Author: chenKai
 * Date: 2023/1/6
 */
@Component
@Slf4j
public abstract class AbstractFlowHandler {

    @Getter
    @Setter
    public AbstractFlowHandler nextHandler;

    @Getter
    @Setter
    public FLowHandlerConfig config;

    /**
     * 处理器执行方法
     * @param applyUser
     * @return
     */
    public abstract JsonData handler(ApplyUserVO applyUser,FLowHandlerConfig config);


    /**
     * 是否满足进入下一步审批资格
     * @param applyUser
     * @return
     */
    protected JsonData nextFLow(ApplyUserVO applyUser){
        if (Objects.isNull(nextHandler)){
            return JsonData.buildSuccess();
        }
        if (applyUser.getApplyAmount().compareTo(config.getMaxMoney()) > 0){
            //大于当前审批人最大审批金额 进入下一个审批阶段
            return nextHandler.handler(applyUser,nextHandler.config);
        }
        return JsonData.buildSuccess();
    }

}
