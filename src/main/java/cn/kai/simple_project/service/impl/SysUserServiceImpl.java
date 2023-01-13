package cn.kai.simple_project.service.impl;

import cn.kai.simple_project.common.utils.StringUtils;
import cn.kai.simple_project.domain.SysUser;
import cn.kai.simple_project.mapper.SysUserMapper;
import cn.kai.simple_project.service.SysUserService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author chenkai
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-01-13 23:44:47
*/
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService{

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public void createUser() {
        List<SysUser> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            SysUser sysUser = new SysUser();
            sysUser.setName(StringUtils.random(6));
            sysUser.setPwd(StringUtils.random(8));
            sysUser.setAddress(StringUtils.random(16));
            list.add(sysUser);
        }
        Integer integer = sysUserMapper.insertBatch(list);
        log.info("批量添加数量:{}",integer);
    }
}




