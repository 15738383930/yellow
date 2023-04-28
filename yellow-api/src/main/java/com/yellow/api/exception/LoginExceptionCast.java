package com.yellow.api.exception;

import com.yellow.common.entity.response.ResultCode;
import com.yellow.common.exception.ExceptionCast;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 * @author zhouhao
 * @date  2021/3/23 11:33
 */
@EqualsAndHashCode(callSuper = true)
public final class LoginExceptionCast extends ExceptionCast {

	/**
	 * 抛出代码异常
	 * @author Hao.
	 * @date 2022/3/31 12:24
	 * @param resultCode 响应代码
	 * @return void
	 */
	public static void cast(ResultCode resultCode){
		throw new LoginException(resultCode);
	}
}
