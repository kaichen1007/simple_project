package cn.kai.simple_project.dto;

import lombok.Data;

/**
 * Author: chenKai
 * Date: 2023/1/16
 */
@Data
public class VisitStatsQueryDto {
    private Long productId;

    private String startTime;

    private String endTime;
}
