package wust.fxp.develop.platfrom.annotation;

import wust.fxp.develop.platfrom.constant.KeyGenerator;
import wust.fxp.develop.platfrom.constant.KeyType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义主键
 * @author 凡兴鹏
 * @create 2021-04-27 22:50
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Key {
    //键类型
    KeyType keyType() default KeyType.PRIMARY_KEY;
    //键生成器
    KeyGenerator keyGrener() default KeyGenerator.BY_UUID;
    //仅对随机生成器有效，表示随机数的最大值(正整数)
    int genNum() default 12;
    //表示键的组别，当是联合主键时有效，表示哪几个联合主键联合唯一
    int groupNum() default 0;

}
