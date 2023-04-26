package com.yellow.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limiting {

//    String value();

    /**
     * 每秒访问频率 默认一秒一次
     */
    double qps() default 1.0;

}
