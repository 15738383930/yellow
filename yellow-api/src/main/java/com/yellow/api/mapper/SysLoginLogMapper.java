package com.yellow.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yellow.api.model.LoginLog;

public interface SysLoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 删除一年前的登录日志
     * @return
     * @author zhouhao
     * @date  2021/4/23 15:47
     */
    Integer deleteLoginLog();
}