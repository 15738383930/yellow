package com.yellow.common.exception;

import com.alibaba.fastjson.JSON;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.CommonCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 手动校验的参数异常
 * @author zhouhao
 * @date  2021/3/23 11:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ValidationParameterException extends RuntimeException {

	@ApiModelProperty("异常参数列表")
	private List<Parameter> parameters;

	public ValidationParameterException(List<Parameter> parameters){
		// 异常信息为【错误代码】+【错误信息】
		super(String.format("%s%s，%s%s%s", Constants.EXCEPTION_CODE, CommonCode.INVALID_PARAM.code(),
				Constants.EXCEPTION_MESSAGE, CommonCode.INVALID_PARAM.message(),
				CollectionUtils.isEmpty(parameters) ? "" : JSON.toJSONString(parameters.get(0))));
		this.parameters = parameters;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Parameter {

		/** 字段名 */
		private String fieldName;

		/** 错误信息 */
		private String message;
	}

}
