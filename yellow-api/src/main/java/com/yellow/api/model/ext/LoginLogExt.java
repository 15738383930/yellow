package com.yellow.api.model.ext;

import com.yellow.api.model.LoginLog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.*;

/**
 * 系统登录日志数据扩展模型
 * 
 * @author Hao.
 * @email 15738383930@163.com
 * @since 2023-05-22 08:58:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统登录日志数据扩展模型")
public class LoginLogExt extends LoginLog implements Serializable {
	private static final long serialVersionUID = 1L;

}
