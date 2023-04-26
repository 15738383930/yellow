package com.yellow.common.exception;

import com.google.common.collect.ImmutableMap;
import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.entity.response.MethodArgumentNotValidResponseResult;
import com.yellow.common.entity.response.ResponseResult;
import com.yellow.common.entity.response.ResultCode;
import com.yellow.common.util.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常捕获处理器<p>
 * 异常捕捉顺序：从上到下依次执行（先捕获自定义异常，再捕获第三方异常，最后捕获未知异常）
 * @author zhouhao
 * @date  2021/3/24 11:02
 */
@Slf4j
@RestControllerAdvice
public class ExceptionCatch {

    /**
     * 使用EXCEPTIONS存放异常类型和错误代码的映射，ImmutableMap的特点的一旦创建不可改变，并且线程安全
     */
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;

    /**
     * 使用builder来构建一个异常类型和错误代码的异常
     */
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    /**
     * 捕获BizException异常
     * @author Hao.
     * @date 2020/4/4 11:42
     * @param e 自定义异常
     * @return ResponseResult
     */
    @ExceptionHandler(BizException.class)
    public ResponseResult customException(BizException e){
        // 记录异常日志
        log.warn("catch BizException : {}", e.getMessage());
        if(e.getResultCode() == null){
            return ResponseResult.get(CommonCode.FAIL,e.getMessage());
        }
        return ResponseResult.get(e.getResultCode());
    }

    /**
     * 捕获非法参数异常
     * @param e
     * @return
     * @author zhouhao
     * @date  2021/4/8 11:30
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult methodArgumentNotValidException(MethodArgumentNotValidException e) {

        // 记录异常日志
        log.warn("catch MethodArgumentNotValidException : {}", e.getMessage());
        BindingResult result = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        result.getFieldErrors().forEach(o ->  map.put(o.getField(), o.getDefaultMessage()));

        return MethodArgumentNotValidResponseResult.get(map);
    }

    /**
     * 捕获Exception异常
     * @author Hao.
     * @date 2020/4/4 11:42
     * @param e 自定义异常
     * @return ResponseResult
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception e){
        if(EXCEPTIONS == null){
            EXCEPTIONS = builder.build();
        }

        final ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        if(resultCode != null){
            // 记录异常日志
            log.warn("catch Exception : {}", ThrowableUtils.getStackTrace(e));
            return ResponseResult.get(resultCode);
        }

        // 记录异常日志
        log.error("catch Exception : {}", ThrowableUtils.getStackTrace(e));
        return ResponseResult.get(CommonCode.SERVER_ERROR);
    }

    static {
        // 在这里加入一些基础的异常类型判断
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
        builder.put(MaxUploadSizeExceededException.class, CommonCode.FILE_SIZE_EXCEEDS_LIMIT);
        builder.put(MissingServletRequestParameterException.class, CommonCode.MISSING_REQUEST_PARAMETER);
        builder.put(HttpRequestMethodNotSupportedException.class, CommonCode.REQUEST_METHOD_ERROR);
        builder.put(HttpMediaTypeNotSupportedException.class, CommonCode.REQUEST_TYPE_ERROR);
    }
}
