package com.yellow.api.aspect;

import com.alibaba.fastjson.JSON;
import com.yellow.api.mapper.SysDetailLogMapper;
import com.yellow.api.mapper.SysLoginLogMapper;
import com.yellow.api.model.request.LoginRequest;
import com.yellow.api.model.response.AuthCode;
import com.yellow.common.constant.Constants;
import com.yellow.api.model.SysDetailLog;
import com.yellow.api.model.SysLoginLog;
import com.yellow.api.model.code.SysDetailLogCode;
import com.yellow.common.entity.response.ResponseResult;
import com.yellow.common.exception.BizException;
import com.yellow.common.exception.ExceptionCast;
import com.yellow.common.util.RedisUtils;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.util.SystemUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 认证授权切面
 * 		可以对：登录失败次数过多进行管制
 * 		可以对：登录、登出日志进行重点记录
 * 		可以对：用户的最后一次登录时间进行记录
 * 		...
 * @author zhouhao
 *
 */
@Aspect
@Component
public class AuthAspect {

	@Resource
	private SysLoginLogMapper sysLoginLogMapper;

    @Resource
    private SysDetailLogMapper sysDetailLogMapper;

	@Resource
	private RedisUtils redisUtils;

	@Pointcut("execution( * com.yellow.api.controller.AuthController.login(..))")
	public void login() {}

	@Pointcut("execution( * com.yellow.api.controller.AuthController.logout(..))")
	public void logout() {}

	@Around("login()")
	public Object auth(final ProceedingJoinPoint pjp) throws Throwable {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) ResponseResult.getRequestAttributesSafely();
		String ip = SystemUtils.getIp(requestAttributes.getRequest());
		if (checkLoginError(ip)) {
			ExceptionCast.cast(AuthCode.AUTH_LOGIN_ERROR_CONTROL);
		}
		Object o = null;
		try {
			o = pjp.proceed();

			// 登录成功，删除登录限制
			deleteLoginErrorTimes(ip);

			// 设置最后一次登录时间
//			setLastLoginTime();
		} catch (BizException e) {
            if(e.getResultCode().code() != AuthCode.AUTH_LOGINED.code()){
                addLoginErrorTimes(ip);
            }
            ExceptionCast.cast(e.getResultCode());
		}
		return o;
	}

	/**
	 * 记录登录日志
	 * @author Hao.
	 * @date 2022/6/27 9:16
	 * @return void
	 */
	@AfterReturning("login()")
	public void loginLog() {
		saveLog(SysLoginLog.builder()
//				.username(JwtUserDetails.getCurrentUserDetail(SysUserService.TOKEN.get()).getUserDetails().getUsername())
				.username(SecurityUtils.getCurrentUsername())
				.status("登录" + Constants.OPERATE_STATUS_SUCCESS).build());
	}

	/**
	 * 记录登出日志
	 * @author Hao.
	 * @date 2022/6/27 9:16
	 * @return void
	 */
	@Before("logout()")
	public void logoutLog() {
		saveLog(SysLoginLog.builder()
				.username(SecurityUtils.getCurrentUsername())
				.status("登出" + Constants.OPERATE_STATUS_SUCCESS).build());
	}

    /**
     * 记录登录异常日志
     * @param point
     * @param e
     * @return void
     * @author zhouhao
     * @date  2021/4/23 10:22
     */
    @AfterThrowing(pointcut = "login()", throwing = "e")
    public void logAfterThrowing(JoinPoint point, Throwable e) {
		SysLoginLog log = SysLoginLog.builder()
				.username(JSON.parseObject(JSON.toJSONString(point.getArgs()[1]), LoginRequest.class).getUsername())
				.status("登录" + Constants.OPERATE_STATUS_FAIL).build();


        saveLog(log);

        sysDetailLogMapper.insert(SysDetailLog.builder()
                .logId(log.getId())
                .logType(Integer.parseInt(SysDetailLogCode.LOG_TYPE_1.k()))
                .detail(SystemUtils.getStackTrace(e)).build());
    }

	/**
	 * 缓存该IP地址用户登录错误次数
	 *
	 * @param ip
	 */
	private void addLoginErrorTimes(String ip) {
		String key = Constants.LOGIN_FAIL_IP + ip;
		if (redisUtils.exists(key)) {
			redisUtils.incrBy(key, 1L);
		} else {
			redisUtils.set(key, "1", 3600L);
		}
	}

	/**
	 * 清除缓存该IP地址用户登录错误次数
	 *
	 * @param ip
	 */
	private void deleteLoginErrorTimes(String ip) {
		String key = Constants.LOGIN_FAIL_IP + ip;
		if (redisUtils.exists(key)) {
			redisUtils.delete(key);
		}
	}

	/**
	 * 检测该IP地址用户登录错误次数是否超过限制
	 * 默认错误次数为5次
	 *
	 * @return
	 */
	private boolean checkLoginError(String ip) {
		boolean b = false;
		String key = Constants.LOGIN_FAIL_IP + ip;
		long max = 5L;
		if (redisUtils.exists(key) && Long.parseLong(redisUtils.get(key)) == max) {
			b = true;
		}
		return b;
	}

	/**
	 * 保存操作日志
	 *
	 * @param result
	 */
	private void saveLog(SysLoginLog result) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		String ip = SystemUtils.getIp(request);

		result.setIp(ip);
		result.setAddress(SystemUtils.getAddress(ip));
		result.setBrowser(SystemUtils.getBrowser(request));
		result.setOs(SystemUtils.getOs(request));
		result.setLoginTime(new Date());

        sysLoginLogMapper.insert(result);
	}
}
