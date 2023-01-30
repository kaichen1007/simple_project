package cn.kai.simple_project.mapper;

import cn.kai.simple_project.common.BaseDomainMapper;
import cn.kai.simple_project.common.BaseMapper;
import cn.kai.simple_project.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chenkai
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2023-01-13 23:44:47
* @Entity cn.kai.simple_project.domain.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseDomainMapper<SysUser> {

}




