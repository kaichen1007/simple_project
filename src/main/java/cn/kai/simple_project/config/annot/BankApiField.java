package cn.kai.simple_project.config.annot;

import java.lang.annotation.*;

/**
 * 用于描述接口的每一个字段规范，包含参数的次序、类型和长度三个属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface BankApiField {

    int order() default -1;
    int length() default -1;
    String type() default "";
}
