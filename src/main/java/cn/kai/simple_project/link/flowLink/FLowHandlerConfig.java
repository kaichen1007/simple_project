package cn.kai.simple_project.link.flowLink;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 审批流程配置
 * Author: chenKai
 * Date: 2023/1/6
 */
@Data
@Builder
public class FLowHandlerConfig {

    private String handler;

    /**
     * 审批人
     */
    private String approveUser;

    /**
     * 审批最小值
     */
    private BigDecimal minMoney;

    /**
     * 审批最大值
     */
    private BigDecimal maxMoney;

    private Boolean down = Boolean.FALSE;

    private FLowHandlerConfig next;


}
