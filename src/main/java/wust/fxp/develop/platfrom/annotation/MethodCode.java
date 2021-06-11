package wust.fxp.develop.platfrom.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 凡兴鹏
 * @create 2021-04-26 13:20
 * 接口描述
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodCode {
    //接口代码
    String value() default "";
    //接口描述
    String desc() default "";
}
