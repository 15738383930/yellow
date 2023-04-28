package com.yellow.api.exception;

import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.ResultCode;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.AuthenticationException;

/**
 * 登录认证异常
 * @author zhouhao
 * @date  2021/3/23 11:33
 */
@EqualsAndHashCode(callSuper = true)
public class LoginException extends AuthenticationException {

	private static final long serialVersionUID = -2636303867711509328L;

	private final ResultCode resultCode;

	public LoginException(ResultCode resultCode){
		// 异常信息为【错误代码】+【错误信息】
		super(String.format("%s%s，%s%s", Constants.EXCEPTION_CODE, resultCode.code(), Constants.EXCEPTION_MESSAGE, resultCode.message()));
		this.resultCode = resultCode;
	}

	public ResultCode getResultCode() {
		return resultCode;
	}
}
