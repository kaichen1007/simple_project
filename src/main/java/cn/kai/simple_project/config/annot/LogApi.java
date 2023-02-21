package cn.kai.simple_project.config.annot;

import cn.kai.simple_project.common.comenum.LogEnum;

import java.lang.annotation.*;

/**
 * Author: chenKai
 * Date: 2023/2/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface LogApi {
    String methodDesc() default "";
    LogEnum type() default LogEnum.UNKNOW;

}
