package wust.fxp.develop.platfrom.annotation;

import wust.fxp.develop.platfrom.constant.ColumnType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 凡兴鹏
 * @create 2021-04-27 22:32
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    //字段的名称
    String value() default "";
    //字段的默认值
    String defaultValue() default "";
    //字段的类型
    ColumnType columnType() default ColumnType.NORMAL_STR;
    //字段的数据库类型
    String dbTypeName() default "";
    //字段注释
    String columComment() default "";

}
