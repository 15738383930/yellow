package com.yellow.api.aspect;

import com.alibaba.fastjson.JSON;
import com.yellow.api.model.SysDetailLog;
import com.yellow.api.model.SysLoginLog;
import com.yellow.api.model.code.SysDetailLogCode;
import com.yellow.api.model.request.LoginRequest;
import com.yellow.api.model.response.AuthCode;
import com.yellow.api.service.SysLogService;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.constant.Constants;
import com.yellow.common.exception.ExceptionCast;
import com.yellow.common.util.RedisUtils;
import com.yellow.common.util.SystemUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
	private SysLogService sysLogService;

	@Resource
	private RedisUtils redisUtils;

	@Resource
	private HttpServletRequest request;

	@Pointcut("execution( * com.yellow.api.controller.AuthController.login(..))")
	public void login() {}

	@Pointcut("execution( * com.yellow.api.controller.AuthController.logout(..))")
	public void logout() {}

    @Before("login()")
	public void loginCheck() {
		// 风控机制校验
		if (checkLoginError(SystemUtils.getIp(request))) {
			ExceptionCast.cast(AuthCode.AUTH_LOGIN_ERROR_CONTROL);
		}
	}

	/**
	 * 记录登录日志
	 * @author Hao.
	 * @date 2022/6/27 9:16
	 * @return void
	 */
	@AfterReturning("login()")
	public void loginSuccess() {
		// 登录成功，删除登录限制
		deleteLoginErrorTimes(SystemUtils.getIp(request));

		sysLogService.saveLoginLog(SysLoginLog.builder()
				.username(SecurityUtils.getCurrentUsername())
				.status("登录" + Constants.OPERATE_STATUS_SUCCESS).build());
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
	public void logonException(JoinPoint point, Throwable e) {

		// 添加登录失败次数
		addLoginErrorTimes(SystemUtils.getIp(request));

		SysLoginLog log = SysLoginLog.builder()
				.username(JSON.parseObject(JSON.toJSONString(point.getArgs()[1]), LoginRequest.class).getUsername())
				.status("登录" + Constants.OPERATE_STATUS_FAIL).build();

		sysLogService.saveLoginLog(log);

		sysLogService.saveDetailLog(SysDetailLog.builder()
				.logId(log.getId())
				.logType(Integer.parseInt(SysDetailLogCode.LOG_TYPE_1.k()))
				.detail(SystemUtils.getStackTrace(e)).build());
	}

	/**
	 * 记录登出日志
	 * @author Hao.
	 * @date 2022/6/27 9:16
	 * @return void
	 */
	@Before("logout()")
	public void logoutLog() {
		sysLogService.saveLoginLog(SysLoginLog.builder()
				.username(SecurityUtils.getCurrentUsername())
				.status("登出" + Constants.OPERATE_STATUS_SUCCESS).build());
	}

	/**
	 * 缓存该IP地址用户登录错误次数
	 *
	 * @param ip
	 */
	public void addLoginErrorTimes(String ip) {
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
	public void deleteLoginErrorTimes(String ip) {
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
	public boolean checkLoginError(String ip) {
		boolean b = false;
		String key = Constants.LOGIN_FAIL_IP + ip;
		long max = 5L;
		if (redisUtils.exists(key) && Long.parseLong(redisUtils.get(key)) >= max) {
			b = true;
		}
		return b;
	}
}
