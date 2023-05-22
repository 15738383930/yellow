package com.yellow.api.mapper;

import com.yellow.api.model.LoginLog;
import com.yellow.api.model.ext.LoginLogExt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.yellow.api.model.request.QueryLoginLogRequest;

import java.util.List;

/**
 * 系统登录日志持久层
 * 
 * @author Hao.
 * @email 15738383930@163.com
 * @since 2023-05-22 09:15:14
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    List<LoginLogExt> findList(QueryLoginLogRequest request);
	
}
