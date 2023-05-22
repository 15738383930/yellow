package com.yellow.common.constant;

/**
 * @Author: fupeiyu
 * @email: fupy3@yellow.cn
 * @Date: 2019/8/12 14:55
 */
public class Constants {

    /**
     * 异常代码
     */
    public static final String EXCEPTION_CODE = "错误代码：";

    /**
     * 异常信息
     */
    public static final String EXCEPTION_MESSAGE = "错误信息：";

    public static final String API_ADD_SYS_USER = "添加系统用户信息";

    public static final String LOGIN_TEXT = "用户登录";

    public static final String LOGOUT_TEXT = "用户登出";

    public static final String API_CHANGE_PASSWORD = "修改密码";

    /**
     * 操作日志状态：成功
     */
    public static final String OPERATE_STATUS_SUCCESS = "成功";

    /**
     * 操作日志状态：失败
     */
    public static final String OPERATE_STATUS_FAIL = "失败";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 神！
     */
    public static final String GOD = "zhouhao";

    public static final String DEFAULT_PASSWORD = "Yellow$123$Yellow";

    public static final String ROLE_PREFIX = "ROLE_";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL_IP = "login_error:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 限流Redis key
     */
    public static final String LIMIT_IP = "limit_ip:";

    /**
     * 令牌自定义标识
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";


    /**
     * 默认每页显示10条
     */
    public static final int PAGE_SIZE_DEFAULT = 10;

    /**
     * 每页显示20条
     */
    public static final int PAGE_SIZE_20 = 20;

    /**
     * 每页最多展示数量
     */
    public static final int PAGE_SIZE_MAX = 100;

    // 默认ip
    public static final String DEFAULT_REQUEST_IP = "127.0.0.1";

    public static final String DICTIONARY_KEY = "dictionary:";

    public static final String DICTIONARY_FLAG = "dictionary";
}
