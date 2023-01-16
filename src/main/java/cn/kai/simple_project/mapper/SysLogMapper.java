package cn.kai.simple_project.mapper;

import cn.kai.simple_project.domain.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author chenkai
* @description 针对表【sys_log】的数据库操作Mapper
* @createDate 2023-01-14 16:44:33
* @Entity cn.kai.simple_project.domain.SysLog
*/
public interface SysLogMapper extends BaseMapper<SysLog> {

    void saveLog(@Param("sysLog") SysLog sysLog);
}




