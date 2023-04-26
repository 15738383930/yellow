package com.yellow.api.aspect;

import com.alibaba.fastjson.JSON;
import com.yellow.api.mapper.SysDetailLogMapper;
import com.yellow.api.mapper.SysOperateLogMapper;
import com.yellow.api.model.SysDetailLog;
import com.yellow.api.model.SysOperateLog;
import com.yellow.api.model.code.SysDetailLogCode;
import com.yellow.common.constant.Constants;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.util.StringUtils;
import com.yellow.common.util.SystemUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * 系统操作日志切面<p>
 *    记录操作日志、操作异常日志
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Resource
    private SysOperateLogMapper sysOperateLogMapper;

    @Resource
    private SysDetailLogMapper sysDetailLogMapper;

    /**
     * 切入点：所有swagger接口
     */
    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void log() {
    }

    /**
     * 记录操作日志
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("log() && @annotation(apiOperation)")
    public Object operateLog(ProceedingJoinPoint point, ApiOperation apiOperation) throws Throwable {
        long start = System.currentTimeMillis();
        Object o = point.proceed();
        long end = System.currentTimeMillis();
        saveOperateLog(point, apiOperation, SysOperateLog.builder().time(end - start).operateStatus(Constants.OPERATE_STATUS_SUCCESS).build());
        return o;
    }

    /**
     * 保存操作日志
     *
     * @param joinPoint
     * @param operation
     * @param log
     */
    private void saveOperateLog(JoinPoint joinPoint, ApiOperation operation, SysOperateLog log) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.setRequestMethod(request.getMethod());

        String operateEvent = operation.value();
        Class<?> clazz = joinPoint.getTarget().getClass();
        if (clazz.isAnnotationPresent(Api.class)) {
            Api annotation = clazz.getAnnotation(Api.class);
            String name = StringUtils.isEmpty(annotation.value()) ? annotation.tags()[0] : annotation.value();
            operateEvent = name + "-" + operateEvent;
        }
        log.setOperateEvent(operateEvent);

        log.setOperateName(SecurityUtils.getCurrentUsername());
        log.setOperateTime(new Date());
        log.setOperateUrl(request.getRequestURL().toString());

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        log.setMethodName(className + "." + methodName + "()");

        log.setParams(JSON.toJSONString(joinPoint.getArgs()));

        sysOperateLogMapper.insert(log);
    }

    /**
     * 记录异常日志
     * @param point
     * @param operation
     * @param e
     * @return void
     * @author zhouhao
     * @date  2021/4/23 10:22
     */
    @AfterThrowing(pointcut = "log() && @annotation(operation)", throwing = "e")
    public void logAfterThrowing(JoinPoint point, ApiOperation operation, Throwable e) {
        SysOperateLog log = SysOperateLog.builder().operateStatus(Constants.OPERATE_STATUS_FAIL).build();
        saveOperateLog(point, operation, log);
        sysDetailLogMapper.insert(SysDetailLog.builder()
                .logId(log.getId())
                .logType(Integer.parseInt(SysDetailLogCode.LOG_TYPE_2.k()))
                .detail(SystemUtils.getStackTrace(e)).build());
    }
}
