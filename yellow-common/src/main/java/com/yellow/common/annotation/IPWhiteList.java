package com.yellow.common.annotation;

import java.lang.annotation.*;

/**
 * IP白名单标记
 * @author Hao.
 * @version 1.0
 * @since 2022/11/1 15:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IPWhiteList {
    int sourceType() default 0;

}
