package com.yellow.api.exception;

import com.yellow.api.model.response.AuthCode;
import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.entity.response.ResponseResult;
import com.yellow.common.exception.BizException;
import com.yellow.common.exception.ExceptionCatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 课程管理自定义异常捕获处理器
 * @author Hao.
 * @version 1.0
 * @date 2020/10/31 9:27
 *
 * @ControllerAdvice 控制器增强
 */
@Slf4j
@ControllerAdvice
public class ApiExceptionCatch extends ExceptionCatch {

    /*
      自定义静态块
     */
    static {
        // 这里，这个异常可以不用定义了。spring security的accessDeniedHandler会捕获该异常
//        builder.put(AccessDeniedException.class, CommonCode.UNAUTHORISE);
        builder.put(BadCredentialsException.class, AuthCode.AUTH_CREDENTIAL_ERROR);
    }

    /**
     * 捕获接口授权异常<p>
     *     如果把AccessDeniedException定义在静态块中，该方法就不用实现了。捕获Exception异常时，会得到AccessDeniedException并返回报错信息
     *     也就是用不到spring security的accessDeniedHandler了
     * @author Hao.
     * @date 2020/4/4 11:42
     * @param e
     * @return ResponseResult
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult accessDeniedException(AccessDeniedException e){
        // 这里，其实可以直接捕获AccessDeniedException，并直接返回
        // 不过，为了把spring security体现的淋漓尽致，还是选择抛出该异常，让spring security去处理
        throw e;
    }

    /**
     * 捕获登录认证异常<p>
     * @author Hao.
     * @date 2020/4/4 11:42
     * @param e
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseResult internalAuthenticationServiceException(InternalAuthenticationServiceException e){
        if (e.getCause() instanceof BizException) {
            return ResponseResult.get(((BizException) e.getCause()).getResultCode());
        }
        return ResponseResult.get(AuthCode.AUTH_LOGIN_ERROR);
    }

    /**
     * 捕获登录超时异常<p>
     * @author Hao.
     * @date 2020/4/4 11:42
     * @param e
     */
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseResult authenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException e){
        return ResponseResult.get(CommonCode.UNAUTHENTICATED);
    }
}
