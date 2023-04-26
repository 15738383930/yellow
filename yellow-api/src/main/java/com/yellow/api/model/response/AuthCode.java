package com.yellow.api.model.response;

import com.yellow.common.entity.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * 认证授权代码
 * @author zhouhao
 * @date  2021/3/23 15:59
 */
@ToString
public enum AuthCode implements ResultCode {
    AUTH_TOKEN_EXPIRED(false, 21000, "您的token令牌已过期，请重新登录！"),
    AUTH_USERNAME_NONE(false,21001,"请输入账号！"),
    AUTH_PASSWORD_NONE(false,21002,"请输入密码！"),
    AUTH_VERIFYCODE_NONE(false,21003,"请输入验证码！"),
    AUTH_ACCOUNT_NOTEXISTS(false,21004,"账号不存在！"),
    AUTH_CREDENTIAL_ERROR(false,21005,"账号或密码错误！"),
    AUTH_LOGIN_ERROR(false,21006,"登陆过程出现异常请尝试重新操作！"),
    AUTH_LOGIN_APPLYTOKEN_FAIL(false,21007,"申请登录授权令牌失败！"),
    AUTH_LOGIN_TOKEN_SAVEFAIL(false,21008,"登录授权令牌保存失败！"),
    AUTH_LOGIN_AUTHSERVER_NOTFOUND(false,21009,"未找到登录认证授权的服务器！"),
    AUTH_LOGIN_ERROR_CONTROL(false, 21010, "登录失败的次数过多，您的电脑被管制一小时，请稍后再试吧！"),
    AUTH_LOGINED(false, 21011, "您已经登录了，请退出登录后再试！"),
    AUTH_LOGOUT_ERROR(false, 21012, "登出失败，请携带您的token令牌，否则无需登出！"),
    AUTH_LOGIN_TIMEOUT(false, 21013, "登录超时，请重新登录！"),
    AUTH_PWD_ERROR(false,21014,"密码错误！"),
    PASSWORD_NEEDS_TO_BE_CHANGED(false,21015,"需要修改密码！"),

    ;

    //操作代码
    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success;

    //操作代码
    @ApiModelProperty(value = "操作代码", example = "22001", required = true)
    int code;
    //提示信息
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;
    private AuthCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
