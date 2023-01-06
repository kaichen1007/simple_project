package cn.kai.simple_project.service;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.vo.ApplyUserVO;

/**
 * Author: chenKai
 * Date: 2023/1/6
 */
public interface FlowService {
    JsonData flowApply(ApplyUserVO applyUserVO);
}
