package com.yellow.api.model.request;

import com.yellow.common.entity.request.RequestData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description 用户登录model
 * @Author zhangz145
 * @Date 2020/5/17 15:05
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "登录请求")
public class LoginRequest extends RequestData {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value="用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value="密码" )
    private String password;

    @NotNull(message = "验证码ID不能为空")
    @ApiModelProperty(value="验证码ID" )
    private String uuid;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value="验证码" )
    private String captcha;

    @ApiModelProperty(value="记住我" )
    private boolean rememberMe;
}
