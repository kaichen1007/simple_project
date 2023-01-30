package cn.kai.simple_project.common;

import cn.kai.simple_project.common.domain.BaseDomain;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: chenKai
 * Date: 2023/1/18
 */
@Mapper
public interface BaseDomainMapper<T extends BaseDomain> extends BaseMapper<T> {
}
