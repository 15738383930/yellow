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
import com.yellow.api.mapper.SysDictionaryMapper;
import com.yellow.api.model.SysDictionary;
import com.yellow.api.model.SysDictionaryData;
import com.yellow.api.model.ext.SysDictionaryExt;
import com.yellow.api.model.request.QuerySysDictionaryRequest;
import com.yellow.api.model.response.SysCode;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.exception.ExceptionCast;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class SysDictionaryService extends ServiceImpl<SysDictionaryMapper, SysDictionary> {

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    @Autowired
    private SysDictionaryDataMapper sysDictionaryDataMapper;

    public QueryResponseResult<SysDictionary> pageList(int page, int size, QuerySysDictionaryRequest request) {
        // 加载分页组件
        PageHelper.startPage(Math.max(page, 1), Math.min((size >= 1 ? size : Constants.PAGE_SIZE_DEFAULT), Constants.PAGE_SIZE_MAX));

        PageSerializable<SysDictionary> pageList = new PageSerializable<>(sysDictionaryMapper.findList(request));

        return QueryResponseResult.success(pageList.getList(), pageList.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(SysDictionary request) {
        // 校验唯一性
        final Integer count = sysDictionaryMapper.selectCount(Wrappers.lambdaQuery(SysDictionary.class).eq(SysDictionary::getDictName, request.getDictName()));
        ExceptionCast.cast(count > 0, SysCode.DICTIONARY_ALREADY_EXISTS);

        baseMapper.insert(request);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(long id, SysDictionary request) {
        final SysDictionary dictionary = findById(id);

        dictionary.setSort(request.getSort());
        dictionary.setRemark(request.getRemark());

        baseMapper.updateById(dictionary);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        for (Long id : ids) {
            final Integer count = sysDictionaryDataMapper.selectCount(Wrappers.lambdaQuery(SysDictionaryData.class).eq(SysDictionaryData::getDictTypeId, id));
            ExceptionCast.cast(count > 0, SysCode.DICTIONARY_EXISTS_DICTIONARY_DATA);
        }
        baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    public SysDictionary findById(long id) {
        final SysDictionary dictionary = sysDictionaryMapper.selectById(id);
        ExceptionCast.cast(Objects.isNull(dictionary), SysCode.DICTIONARY_NOT_EXIST);
        return dictionary;
    }

    public List<SysDictionaryExt> getAllList() {
        List<SysDictionaryExt> result = new ArrayList<>();

        final List<SysDictionary> dictionaryList = baseMapper.findDictionaryList();
        final List<SysDictionaryData> dictionaryDataList = sysDictionaryDataMapper.findDictDataList();
        for(SysDictionary type : dictionaryList){
            SysDictionaryExt ext = new SysDictionaryExt();
            BeanUtils.copyProperties(type, ext);
            for(SysDictionaryData data : dictionaryDataList){
                if(type.getId().equals(data.getDictTypeId())){
                    ext.getData().add(data);
                }
            }
            result.add(ext);
        }
        return result;
    }

}