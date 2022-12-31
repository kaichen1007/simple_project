package cn.kai.simple_project.controller;

import cn.kai.simple_project.app.bank.CreateUserApi;
import cn.kai.simple_project.app.bank.PayApi;
import cn.kai.simple_project.common.domain.JsonData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * Author: chenKai
 * Date: 2022/12/31
 */
@RestController
@RequestMapping(value = "/bank")
public class BankApiController {

    @GetMapping(value = "/createUser")
    public JsonData createUser(@RequestParam String param){
        CreateUserApi userApi = JSONObject.parseObject(param, CreateUserApi.class);
        return JsonData.buildSuccess(userApi);
    }

    @GetMapping(value = "/pay")
    public JsonData pay(@RequestParam String param){
        PayApi payApi = JSONObject.parseObject(param, PayApi.class);
        return JsonData.buildSuccess(payApi);
    }


}
