package cn.kai.simple_project.controller;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.domain.SysUser;
import cn.kai.simple_project.mapper.SysUserMapper;
import cn.kai.simple_project.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author: chenKai
 * Date: 2023/1/14
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService service;

    @Resource
    private SysUserMapper sysUserMapper;

    @GetMapping("/createUser")
    public JsonData createUser(){
        service.createUser();
        return JsonData.buildSuccess();
    }

    @PostMapping("/getUser")
    public JsonData getUser(@RequestBody SysUser sysUser){
        return JsonData.buildSuccess(sysUserMapper.selectByUuid(sysUser));
    }
}
