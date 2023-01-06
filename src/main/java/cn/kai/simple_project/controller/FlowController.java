package cn.kai.simple_project.controller;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.service.FlowService;
import cn.kai.simple_project.vo.ApplyUserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Author: chenKai
 * Date: 2023/1/6
 */
@RestController
@RequestMapping(value = "/flow")
public class FlowController {


    @Resource
    private FlowService flowService;

    @PostMapping(value = "/flowApply")
    public JsonData flowApply(@RequestBody ApplyUserVO applyUserVO){
        return flowService.flowApply(applyUserVO);
    }


}
