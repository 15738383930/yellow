package com.yellow.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yellow.api.model.SysLoginLog;

public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

    /**
     * 删除一年前的登录日志
     * @return
     * @author zhouhao
     * @date  2021/4/23 15:47
     */
    Integer deleteLoginLog();
}