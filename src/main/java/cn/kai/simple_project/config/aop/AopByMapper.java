package cn.kai.simple_project.config.aop;

import cn.kai.simple_project.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * mapper aop操作
 * 插入和修改
 * Author: chenKai
 * Date: 2023/1/13
 */

@Slf4j
@Aspect
@Component
public class AopByMapper {

    private static final String SAVE_MAPPER = "execution(* cn.kai.*.mapper..*.insert*(..)) || execution(* cn.*.*..service.*.saveBatch(..)) || execution(* cn.kai.*.mapper..*.insertBatch(..))";
    private static final String UPDATE_MAPPER = "execution(* cn.kai.*.mapper..*.update(..)) || execution(* cn.kai.*.mapper..*.updateById(..)) || execution(* cn.kai.*.mapper..*.updateBatchById(..)) || execution(* cn.kai.*.mapper..*.saveOrUpdateBatch(..))";

    @Around("("+ SAVE_MAPPER +")")
    public Object setInsert(ProceedingJoinPoint joinPoint) throws Throwable {
        Date now = new Date();
        //获取参数
        Object domain = joinPoint.getArgs()[0];
        Collection<Object> domainList = null;

        if (Objects.isNull(domain)){
            log.error("mapper插入AOP拦截,参数为空");
            return joinPoint.proceed();
        }

        if (domain instanceof Collection){
            //判断是否是集合 若是则为批量操作
            domainList =  (Collection<Object>) domain;
            log.info("====== <Mapper AOP批量插入拦截,插入条数:{}> ======",domainList.size());
        }else {
            domainList = new ArrayList<>();
            domainList.add(domain);
            log.info("====== <Mapper AOP单条插入拦截> ======");
        }

        //转为迭代器
        Iterator<Object> iterator = domainList.iterator();
        while (iterator.hasNext()){
            Object obj = iterator.next();
            Class<?> aClass = obj.getClass();
            if (StringUtils.isEmptyAll(JSON.parseObject(JSON.toJSONString(obj)).getString("uuid"))){
                //判断uuid是否存在 如不存在则插入
                aClass.getMethod("setUuid",String.class).invoke(obj, StringUtils.getUuid());
            }
            //插入创建时间
            aClass.getMethod("setCreateTime",Date.class).invoke(obj,now);
            //插入修改时间
            aClass.getMethod("setUpdateTime",Date.class).invoke(obj,now);
            //插入逻辑删除
            aClass.getMethod("setDelFlag",Integer.class).invoke(obj,0);
            //主键id设为空
            aClass.getMethod("setId",Integer.class).invoke(obj, (Object) null);
        }
        //回转到参数
        joinPoint.getArgs()[0] = domainList;
        return joinPoint.proceed();
    }

    @Around("("+ UPDATE_MAPPER +")")
    public Object setUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        Date now = new Date();
        Object domain = joinPoint.getArgs()[0];
        Collection<Object> domainList = null;

        if (Objects.isNull(domain)){
            log.error("mapper更新AOP拦截,参数为空");
            return joinPoint.proceed();
        }
        if (domain instanceof Collection){
            domainList = (Collection<Object>) domain;
            log.info("====== <Mapper AOP批量插入拦截,插入条数:{}> ======",domainList.size());
        }

        Iterator<Object> iterator = domainList.iterator();

        while (iterator.hasNext()){
            Object obj = iterator.next();
            Class<?> aClass = obj.getClass();

            //修改更新时间
            aClass.getMethod("setUpdateTime",Date.class).invoke(obj, now);
        }

        joinPoint.getArgs()[0] = domainList;

        return joinPoint.proceed();
    }





}
