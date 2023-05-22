package com.yellow.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yellow.api.model.SysDictionary;
import com.yellow.api.model.SysOperateLog;
import com.yellow.api.model.request.QuerySysDictionaryRequest;
import com.yellow.api.model.request.QuerySysLogRequest;

import java.util.List;

public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {

    /**
     * 字典列表
     */
    List<SysDictionary> findDictionaryList();

    List<SysDictionary> findList(QuerySysDictionaryRequest request);
}