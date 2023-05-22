/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yellow.api.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.yellow.api.mapper.SysDictionaryDataMapper;
import com.yellow.api.model.SysDictionaryData;
import com.yellow.api.model.response.SysCode;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.exception.ExceptionCast;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class SysDictionaryDataService extends ServiceImpl<SysDictionaryDataMapper, SysDictionaryData> {

    public QueryResponseResult<SysDictionaryData> pageList(int dictionaryid, int page, int size) {
        // 加载分页组件
        PageHelper.startPage(Math.max(page, 1), Math.min((size >= 1 ? size : Constants.PAGE_SIZE_DEFAULT), Constants.PAGE_SIZE_MAX));
        final List<SysDictionaryData> list = baseMapper.selectList(Wrappers.lambdaQuery(SysDictionaryData.class)
                .eq(SysDictionaryData::getDictTypeId, dictionaryid).orderByAsc(SysDictionaryData::getSort));
        PageSerializable<SysDictionaryData> pageList = new PageSerializable<>(list);

        return QueryResponseResult.success(pageList.getList(), pageList.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(SysDictionaryData request) {
        // 校验唯一性
        final Integer count = baseMapper.selectCount(Wrappers.lambdaQuery(SysDictionaryData.class).eq(SysDictionaryData::getDictTypeId, request.getDictTypeId())
                .and(o -> o.eq(SysDictionaryData::getDictLabel, request.getDictLabel()).or().eq(SysDictionaryData::getDictValue, request.getDictValue())));
        ExceptionCast.cast(count > 0, SysCode.DICTIONARY_DATA_ALREADY_EXISTS);

        baseMapper.insert(request);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(long id, SysDictionaryData request) {
        final SysDictionaryData dictionary = findById(id);

        dictionary.setSort(request.getSort());
        dictionary.setRemark(request.getRemark());

        baseMapper.updateById(dictionary);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    public SysDictionaryData findById(Long id) {
        final SysDictionaryData sysDictionaryData = baseMapper.selectById(id);
        ExceptionCast.cast(Objects.isNull(sysDictionaryData), SysCode.DICTIONARY_DATA_NOT_EXIST);
        return sysDictionaryData;
    }
}