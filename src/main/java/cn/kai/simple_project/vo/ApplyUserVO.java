package cn.kai.simple_project.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 被审批人
 * Author: chenKai
 * Date: 2023/1/6
 */
@Data
@Builder
public class ApplyUserVO {

    private String userName;

    private BigDecimal applyAmount;
}
