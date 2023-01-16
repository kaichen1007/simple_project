package cn.kai.simple_project.mapper;

import cn.kai.simple_project.domain.VisitStatus;
import cn.kai.simple_project.dto.VisitStatsQueryDto;
import cn.kai.simple_project.vo.VisitStatsVo;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author: chenKai
 * Date: 2023/1/16
 */
@Mapper
public interface VisitStatusMapper extends BaseMapper<VisitStatus> {

    @DS("slave")
    List<VisitStatus> getList();

    /**
     * 查询某个产品的一段时间内各地区的访问量
     * @param dto
     * @return
     */
    List<VisitStatsVo> getProvincePv(@Param("dto") VisitStatsQueryDto dto);



}
