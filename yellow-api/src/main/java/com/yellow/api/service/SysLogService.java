package com.yellow.api.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.yellow.api.mapper.SysOperateLogMapper;
import com.yellow.api.model.SysOperateLog;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.QueryResponseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLogService {

    @Resource
    private SysOperateLogMapper sysOperateLogMapper;

    public QueryResponseResult<SysOperateLog> pageList(int page, int size) {
        // 加载分页组件
        PageHelper.startPage(Math.max(page, 1), Math.min((size >= 1 ? size : Constants.PAGE_SIZE_DEFAULT), Constants.PAGE_SIZE_MAX));

        PageSerializable<SysOperateLog> pageList = new PageSerializable<>(sysOperateLogMapper.selectList(Wrappers.lambdaQuery(SysOperateLog.class).orderByDesc(SysOperateLog::getOperateTime)));

        return QueryResponseResult.success(pageList.getList(), pageList.getTotal());
    }

}
