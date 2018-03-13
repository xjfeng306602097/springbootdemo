package com.xjf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyRequestMapping {

    /**
     * 表示访问该方法的url
     * @return
     */
    String value() default "";

    /**
     * 表示请求该方法的方式
     * @return
     */
     MyRequestMethod[] method() default {};

}
