package cn.kai.simple_project.vo;

import cn.kai.simple_project.domain.VisitStatus;
import lombok.Data;

/**
 * Author: chenKai
 * Date: 2023/1/16
 */
@Data
public class VisitStatsVo extends VisitStatus {

    private Long pvCount=0L;

    /**
     * 时间的字符串映射，天、小时
     */
    private String dateTimeStr;
}
