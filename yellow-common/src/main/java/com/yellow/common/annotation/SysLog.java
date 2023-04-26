package com.yellow.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * <p>emmm...弃用的原因，在于，我们都懒得用这个注解。</p>
 * <p>反正需要用swagger的{@link io.swagger.annotations.ApiOperation}，它呢，刚好满足我们对全局日志的需要。</p>
 * <p>So，你懂得！</p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface SysLog {

    String value() default "";
}
