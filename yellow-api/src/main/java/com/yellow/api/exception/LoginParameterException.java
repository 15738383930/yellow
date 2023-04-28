package com.yellow.api.exception;

import com.alibaba.fastjson.JSON;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.entity.response.ResultCode;
import com.yellow.common.exception.ValidationParameterException;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

/**
 * 登录参数异常
 * @author zhouhao
 * @date  2021/3/23 11:33
 */
@EqualsAndHashCode(callSuper = true)
public class LoginParameterException extends AuthenticationException {

	@ApiModelProperty("异常参数列表")
	private List<ValidationParameterException.Parameter> parameters;

	public LoginParameterException(List<ValidationParameterException.Parameter> parameters){
		// 异常信息为【错误代码】+【错误信息】
		super(String.format("%s%s，%s%s%s", Constants.EXCEPTION_CODE, CommonCode.INVALID_PARAM.code(),
				Constants.EXCEPTION_MESSAGE, CommonCode.INVALID_PARAM.message(), JSON.toJSONString(parameters.get(0))));
		this.parameters = parameters;
	}

	public List<ValidationParameterException.Parameter> getParameters() {
		return parameters;
	}
}
