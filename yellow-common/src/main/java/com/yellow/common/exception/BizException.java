package com.yellow.common.exception;

import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 * @author zhouhao
 * @date  2021/3/23 11:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {

	private static final long serialVersionUID = -2636303867711509328L;

	private ResultCode resultCode;

	public BizException(ResultCode resultCode){
		// 异常信息为【错误代码】+【错误信息】
		super(String.format("%s%s，%s%s", Constants.EXCEPTION_CODE, resultCode.code(), Constants.EXCEPTION_MESSAGE, resultCode.message()));
		this.resultCode = resultCode;
	}

	public BizException(String msg){
		super(msg);
	}

}
