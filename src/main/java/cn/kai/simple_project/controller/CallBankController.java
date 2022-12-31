package cn.kai.simple_project.controller;

import cn.kai.simple_project.app.bank.BankService;
import cn.kai.simple_project.app.bank.CreateUserApi;
import cn.kai.simple_project.app.bank.PayApi;
import cn.kai.simple_project.common.comenum.BizCodeEnum;
import cn.kai.simple_project.common.domain.CommonRequestDomain;
import cn.kai.simple_project.common.domain.JsonData;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Author: chenKai
 * Date: 2022/12/31
 */
@RestController
@RequestMapping(value = "/call/bank")
public class CallBankController {

    @Resource
    private BankService bankService;


    @PostMapping(value = "/callBank")
    public JsonData callBank(@RequestBody CommonRequestDomain requestDomain){
        if (BizCodeEnum.CREATE_USER.getMessage().equalsIgnoreCase(requestDomain.getType())){
            CreateUserApi api = new CreateUserApi();
            api.setName("陈凯");
            api.setMobile("15517582891");
            api.setIdentity("411");
            api.setAge(24);
            return JsonData.buildSuccess(bankService.remoteCall(requestDomain.getCreateUserApi()));
        }else if (BizCodeEnum.PAY.getMessage().equalsIgnoreCase(requestDomain.getType())){
            PayApi api = new PayApi();
            api.setUserId(1);
            api.setAmount(new BigDecimal("2000"));
            return JsonData.buildSuccess(bankService.remoteCall(requestDomain.getPayApi()));
        }
        return JsonData.buildResult(BizCodeEnum.PARAM_ERROR);
    }

}
