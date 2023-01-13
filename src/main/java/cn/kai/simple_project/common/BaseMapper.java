package cn.kai.simple_project.common;

import cn.kai.simple_project.common.constans.GlobalConstant;
import cn.kai.simple_project.common.domain.BaseDomain;
import cn.kai.simple_project.config.db.InsertBatchSqlInjector;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.Iterators;

import java.sql.Wrapper;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * 重写BaseMapper
 * Author: chenKai
 * Date: 2023/1/13
 */
public interface BaseMapper<T extends  BaseDomain> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    default T selectByUuid(String uuid){
        return selectOne(new QueryWrapper<T>()
                .lambda()
                .eq(BaseDomain::getUuid,uuid)
                .eq(BaseDomain::getDelFlag,GlobalConstant.DEL_FLAG));
    }

    /**
     * 适用于修改单个字段
     * @param uuid
     * @param modify
     * @return
     */
    default T updateByUuid(String uuid, Consumer<T> modify){
        // updateByUuid(uuid,domain->domain.setName(name))
        T t = selectByUuid(uuid);
        modify.accept(t);
        updateById(t);
        return t;
    }

    /**
     * 传入对象，修改
     * @param uuid
     * @param t
     * @return
     */
    default Integer updateByUuid(String uuid,T t){
        return update(t,new UpdateWrapper<T>()
                .lambda()
                .eq(BaseDomain::getUuid,uuid)
                .eq(BaseDomain::getDelFlag,GlobalConstant.DEL_FLAG));
    }

    /**
     * 逻辑删除
     * @param uuid
     * @return
     */
    default Integer delLogicByUuid(String uuid){
        return update(null,new LambdaUpdateWrapper<T>()
                .set(BaseDomain::getDelFlag,GlobalConstant.UN_DEL_FLAG)
                .eq(BaseDomain::getUuid,uuid)
                .eq(BaseDomain::getDelFlag,GlobalConstant.DEL_FLAG));
    }

    /**
     * 批量添加
     * @param list
     * @return
     */
    default Integer insertBatch(Collection<T> list){
        return insertBatchSomeColumn((List<T>) list);
    }

    //批量插入
    int insertBatchSomeColumn(@Param("list") List<T> batchList);


}
