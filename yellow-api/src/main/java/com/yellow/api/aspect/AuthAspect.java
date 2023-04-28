package com.yellow.api.aspect;

import com.alibaba.fastjson.JSON;
import com.yellow.api.exception.LoginExceptionCast;
import com.yellow.api.mapper.SysDetailLogMapper;
import com.yellow.api.mapper.SysLoginLogMapper;
import com.yellow.api.model.SysDetailLog;
import com.yellow.api.model.SysLoginLog;
import com.yellow.api.model.code.SysDetailLogCode;
import com.yellow.api.model.response.AuthCode;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.constant.Constants;
import com.yellow.common.util.RedisUtils;
import com.yellow.common.util.SystemUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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

	@Resource
	private HttpServletRequest request;

	@Pointcut("execution( * com.yellow.api.security.JwtAuthenticationLoginFilter.attemptAuthentication(..))")
	public void login() {}

	@Pointcut("execution( * com.yellow.api.security.JwtAuthenticationLoginFilter.successfulAuthentication(..))")
	public void loginSuccess() {}

	@Pointcut("execution( * com.yellow.api.security.JwtAuthenticationLoginFilter.unsuccessfulAuthentication(..))")
	public void loginFail() {}

    @Pointcut("execution( * com.yellow.api.controller.AuthController.logout(..))")
    public void logout() {};

    @Before("login()")
	public void auth() {
		if (checkLoginError(SystemUtils.getIp(request))) {
			LoginExceptionCast.cast(AuthCode.AUTH_LOGIN_ERROR_CONTROL);
		}
	}

	/**
	 * 记录登录日志
	 * @AfterReturning：如果出现异常，该方法不会执行
	 * @author Hao.
	 * @date 2022/6/27 9:16
	 * @return void
	 */
	@AfterReturning("loginSuccess()")
	public void success() {
		// 登录成功，删除登录限制
		deleteLoginErrorTimes(SystemUtils.getIp(request));

		// 设置最后一次登录时间
//			setLastLoginTime();

		saveLog(SysLoginLog.builder()
				.username(SecurityUtils.getCurrentUsername())
				.status("登录" + Constants.OPERATE_STATUS_SUCCESS).build());
	}

	/**
	 * 记录登录异常日志
	 * @AfterReturning：如果出现异常，该方法不会执行
	 * @return void
	 * @author zhouhao
	 * @date  2021/4/23 10:22
	 */
	@AfterReturning(value = "loginFail()", returning = "result")
	public void logAfterThrowing(JoinPoint joinPoint, Object result) {
		// 登录失败，风控次数+1
		addLoginErrorTimes(SystemUtils.getIp(request));

		SysLoginLog log = SysLoginLog.builder()
				.username(SecurityUtils.getCurrentUsername())
				.status("登录" + Constants.OPERATE_STATUS_FAIL).build();

		saveLog(log);

		sysDetailLogMapper.insert(SysDetailLog.builder()
				.logId(log.getId())
				.logType(Integer.parseInt(SysDetailLogCode.LOG_TYPE_1.k()))
				.detail(JSON.toJSONString(result)).build());
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
		if (redisUtils.exists(key) && Long.parseLong(redisUtils.get(key)) >= max) {
			b = true;
		}
		return b;
	}

	/**
	 * 保存登录日志
	 *
	 * @param result
	 */
	private void saveLog(SysLoginLog result) {
		String ip = SystemUtils.getIp(request);

		result.setIp(ip);
		result.setAddress(SystemUtils.getAddress(ip));
		result.setBrowser(SystemUtils.getBrowser(request));
		result.setOs(SystemUtils.getOs(request));
		result.setLoginTime(new Date());

        sysLoginLogMapper.insert(result);
	}
}
