package wust.fxp.develop.platfrom.annotation;

import wust.fxp.develop.platfrom.constant.DbType;
import wust.fxp.develop.platfrom.constant.StyleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 凡兴鹏
 * @create 2021-04-27 22:20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    //表名
    String value() default "";
    //表名
    String tableName() default "";
    //表注释
    String tableDesc() default "";
    //数据库类型
    DbType dbType() default DbType.ORACLE;
    //表名风格类型
    StyleType modelFormat() default StyleType.UNDER_LINE;
}
