package com.yellow.api.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yellow.api.model.SysOperateLog;
import com.yellow.api.model.request.QuerySysLogRequest;

import java.util.List;

public interface SysOperateLogMapper extends BaseMapper<SysOperateLog> {

    /**
     * 删除一年前的操作日志
     * @return
     * @author zhouhao
     * @date  2021/4/23 15:47
     */
    Integer deleteOperateLog();

    List<SysOperateLog> findList(QuerySysLogRequest request);
}