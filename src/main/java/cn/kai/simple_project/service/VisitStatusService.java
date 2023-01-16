package cn.kai.simple_project.service;

import cn.kai.simple_project.domain.VisitStatus;
import cn.kai.simple_project.dto.VisitStatsQueryDto;
import cn.kai.simple_project.vo.VisitStatsVo;

import java.util.List;
import java.util.Map;

/**
 * Author: chenKai
 * Date: 2023/1/16
 */
public interface VisitStatusService {
    /**
     * 获取list
     * @return
     */
    List<VisitStatus> getVisitStatusList();

    /**
     * 查询某个产品的一段时间内各地区的访问量
     * @param dto
     * @return
     */
    List<VisitStatsVo> getProvincePv(VisitStatsQueryDto dto);
}
