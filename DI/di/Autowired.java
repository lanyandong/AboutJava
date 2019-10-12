package cn.ydlan.di;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 定义一个注解
 * @Retention是元注解，并将其保留到运行时
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
}
