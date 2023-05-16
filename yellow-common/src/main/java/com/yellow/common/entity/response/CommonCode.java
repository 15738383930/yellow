package com.yellow.common.entity.response;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用代码
 * @author zhouhao
 * @date  2021/3/23 11:28
 */
@ToString
@Slf4j
public enum CommonCode implements ResultCode {

    SUCCESS(true,10000,"操作成功！"),
    FAIL(false,11111,"操作失败！"),
    UNAUTHENTICATED(false,10001,"此操作需要登陆系统！"),
    UNAUTHORISE(false,10002,"权限不足，无权操作！"),
    INVALID_PARAM(false,10003,"非法参数！"),
    FILE_SIZE_EXCEEDS_LIMIT(false,10004,"文件上传的大小超过限制！"),
    MISSING_REQUEST_PARAMETER(false,10005,"缺少请求参数！"),
    VERIFICATION_CODE_ERROR(false,10006,"验证码错误！（不存在、错误或过期）"),
    VERIFICATION_CODE_EXPIRED(false,10007,"验证码过期！"),
    REQUEST_METHOD_ERROR(false,10008,"不支持的Http请求方法！（检查你的请求方法：fail、post等）"),
    DUPLICATE_TOKEN(false,10009,"token重复！"),
    REQUEST_TYPE_ERROR(false,10010,"不支持的Http请求类型！（检查你的请求类型：application/x-www-form-urlencoded、application/json等）"),
    TOKEN_EXPIRED(false,10011,"token过期！"),
    REQUEST_TOO_FAST(false,10012,"你访问的太频繁了，慢点哦~"),
    REQUEST_DATA_END_ERROR(false,10013,"请求数据解析异常！"),
    RESPONSE_DATA_END_ERROR(false,10014,"数据解析异常！"),
    REQUEST_DATA_SIGN_ERROR(false,10015,"数据解签异常！"),
    IP_WHITE_LIST_NOT_EXIST(false,10016,"您不在IP白名单，无权操作！"),

    IP_BLACK_PERMISSION(false,10017,"IP限制，无权操作！"),

    THIRD_TOKEN_ERROR(false,10018,"认证失败，请登录！"),
    VERIFICATION_ID_NOT_NULL(false,10019,"验证码ID不能为空！"),

    PUBLIC_KEY_INVALID(false,10020,"错误/无效的公钥！"),

    GOD_UNSHAKABLE(false,19999,"神不可动摇！"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),
    ;

    boolean success;

    int code;

    String message;

    CommonCode(boolean success, int code, String message){
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
