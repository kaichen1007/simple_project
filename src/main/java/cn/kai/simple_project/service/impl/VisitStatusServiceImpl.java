package cn.kai.simple_project.service.impl;

import cn.kai.simple_project.domain.VisitStatus;
import cn.kai.simple_project.dto.VisitStatsQueryDto;
import cn.kai.simple_project.mapper.VisitStatusMapper;
import cn.kai.simple_project.service.VisitStatusService;
import cn.kai.simple_project.vo.VisitStatsVo;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Author: chenKai
 * Date: 2023/1/16
 */
@Service
public class VisitStatusServiceImpl implements VisitStatusService {

    @Resource
    private VisitStatusMapper visitStatusMapper;

    /**
     * 获取list
     * @return
     */
    @Override
    @DS("slave")
    public List<VisitStatus> getVisitStatusList() {
//        List<VisitStatus> visitStatuses  = visitStatusMapper.getList();
        List<VisitStatus> visitStatuses = visitStatusMapper.selectList(new QueryWrapper<VisitStatus>());
        if (!visitStatuses.isEmpty()){
            return visitStatuses;
        }
        return null;
    }

    /**
     * 查询某个产品的一段时间内各地区的访问量
     * @param dto
     * @return
     */
    @Override
    @DS("slave")
    public List<VisitStatsVo> getProvincePv(VisitStatsQueryDto dto) {
        List<VisitStatsVo> result = visitStatusMapper.getProvincePv(dto);
        if (!result.isEmpty()){
            return result;
        }
        return null;
    }
}
