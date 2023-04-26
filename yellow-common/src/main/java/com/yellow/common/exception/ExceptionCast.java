package com.yellow.common.exception;

import com.yellow.common.entity.response.ResultCode;

/**
 * 异常抛出类
 * @author zhouhao
 * @date  2021/3/23 11:33
 */
public class ExceptionCast {

    /**
     * 抛出代码异常
     * @author Hao.
     * @date 2022/3/31 12:24
     * @param resultCode 响应代码
     * @return void
     */
    public static void cast(ResultCode resultCode){
        throw new BizException(resultCode);
    }

    /**
     * 根据条件抛出代码异常
     * @author Hao.
     * @date 2022/3/31 12:24
     * @param condition 条件-条件为真，抛出
     * @param resultCode 响应代码
     * @return void
     */
    public static void cast(boolean condition, ResultCode resultCode){
        if(condition){
            ExceptionCast.cast(resultCode);
        }
    }
}
