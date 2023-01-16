package cn.kai.simple_project.controller;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.domain.VisitStatus;
import cn.kai.simple_project.dto.VisitStatsQueryDto;
import cn.kai.simple_project.service.VisitStatusService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * clickhouse dome
 * Author: chenKai
 * Date: 2023/1/16
 */
@RestController
@RequestMapping("/clickhouse")
public class VisitStatusController {

    @Resource
    private VisitStatusService visitStatusService;

    @GetMapping("/list")
    public JsonData getVisitStatusList(){
        return JsonData.buildSuccess(visitStatusService.getVisitStatusList());
    }


    @PostMapping("/getProvincePv")
    public JsonData getProvincePv(@RequestBody VisitStatsQueryDto dto){
        return JsonData.buildSuccess(visitStatusService.getProvincePv(dto));
    }

}
