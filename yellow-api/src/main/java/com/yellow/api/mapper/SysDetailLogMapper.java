package com.yellow.api.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yellow.api.model.SysDetailLog;

public interface SysDetailLogMapper extends BaseMapper<SysDetailLog> {

    /**
     * 删除半年前操作日志的异常信息
     * @return
     * @author zhouhao
     * @date  2021/4/23 15:47
     */
    Integer deleteOperateLog();

    /**
     * 删除一年前登录日志的异常信息
     * @return
     * @author zhouhao
     * @date  2021/4/23 15:47
     */
    Integer deleteLoginLog();
}