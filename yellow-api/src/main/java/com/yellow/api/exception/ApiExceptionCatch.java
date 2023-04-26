package com.yellow.api.exception;

import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.exception.ExceptionCatch;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 课程管理自定义异常捕获处理器
 * @author Hao.
 * @version 1.0
 * @date 2020/10/31 9:27
 *
 * @ControllerAdvice 控制器增强
 */
@ControllerAdvice
public class ApiExceptionCatch extends ExceptionCatch {

    /**
     * 自定义静态块
     */
    static {
        builder.put(AccessDeniedException.class, CommonCode.UNAUTHORISE);
    }
}
