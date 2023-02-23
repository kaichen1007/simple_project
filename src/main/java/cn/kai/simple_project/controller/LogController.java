package cn.kai.simple_project.controller;

import cn.kai.simple_project.common.comenum.LogEnum;
import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.config.annot.LogApi;
import cn.kai.simple_project.dto.Shop;
import cn.kai.simple_project.service.LogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author: chenKai
 * Date: 2023/2/21
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Resource
    private LogService logService;


    @PostMapping("/getLog")
    @LogApi(methodDesc = "获取日志",type = LogEnum.SELECT)
    public JsonData getLog(@RequestBody Shop shop){
        Integer a =  shop.getShopId() / 0;
        return JsonData.buildSuccess(shop);
    }


}
