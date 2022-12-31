package cn.kai.simple_project.config.annot;

import java.lang.annotation.*;

/**
 * 自定义银行注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface BankApi {
    /**
     * 接口地址说明
     * @return
     */
    String desc() default "";

    /**
     * url
     * @return
     */
    String url() default "";

}
