package wust.fxp.develop.platfrom.annotation;

/**
 * 用在链式接口上，用来声明这是一个链式类
 * @author 凡兴鹏
 * @create 2021-05-05 23:09
 */
public @interface Link {
    //用于唯一标识链
   String value() default "";
}
