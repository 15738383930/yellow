package com.yellow.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yellow.api.model.SysDictionaryData;

import java.util.List;

public interface SysDictionaryDataMapper extends BaseMapper<SysDictionaryData> {

    /**
     * 字典数据列表
     */
    List<SysDictionaryData> findDictDataList();
}