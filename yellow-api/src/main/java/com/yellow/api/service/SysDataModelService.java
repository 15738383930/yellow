package com.yellow.api.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.yellow.api.mapper.SysDataModelMapper;
import com.yellow.api.mapper.SysModelDictionaryMapper;
import com.yellow.api.model.SysDataModel;
import com.yellow.api.model.SysModelDictionary;
import com.yellow.api.model.ext.SysDataModelExt;
import com.yellow.api.model.response.SysCode;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.exception.ExceptionCast;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysDataModelService extends ServiceImpl<SysDataModelMapper, SysDataModel> {

    @Autowired
    private SysDataModelMapper sysDataModelMapper;

    @Autowired
    private SysModelDictionaryMapper sysModelDictionaryMapper;

    public QueryResponseResult<SysDataModel> pageList(int page, int size) {
        // 加载分页组件
        PageHelper.startPage(Math.max(page, 1), Math.min((size >= 1 ? size : Constants.PAGE_SIZE_DEFAULT), Constants.PAGE_SIZE_MAX));

        final List<SysDataModel> list = baseMapper.selectList(Wrappers.lambdaQuery(SysDataModel.class).orderByAsc(SysDataModel::getSort));
        PageSerializable<SysDataModel> pageList = new PageSerializable<>(list);

        return QueryResponseResult.success(pageList.getList(), pageList.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(SysDataModelExt request) {
        // 校验唯一性
        final Integer count = sysDataModelMapper.selectCount(Wrappers.lambdaQuery(SysDataModel.class).eq(SysDataModel::getModelName, request.getModelName())
                .or().eq(SysDataModel::getModelType, request.getModelType()));
        ExceptionCast.cast(count > 0, SysCode.DATA_MODEL_ALREADY_EXISTS);

        baseMapper.insert(request);

        // 得到数据模型id
        final Integer id = request.getId();

        // 2. 添加数据模型与系统字典关联信息
        request.getDictionaryIds().removeIf(o -> o <= 0);
        request.getDictionaryIds().forEach(o -> sysModelDictionaryMapper.insert(SysModelDictionary.builder().modelId(id).dictionaryId(o).build()));
    }

    @CacheEvict(value = "dictionary", key = "#modelType")
    @Transactional(rollbackFor = Exception.class)
    public void update(int id, String modelType, SysDataModelExt request) {
        final SysDataModel data = findById(id);

        data.setSort(request.getSort());
        data.setRemark(request.getRemark());

        baseMapper.updateById(data);

        // 删除历史关联信息
        sysModelDictionaryMapper.delete(Wrappers.lambdaQuery(SysModelDictionary.class).eq(SysModelDictionary::getModelId, id));
        // 添加新关联信息
        request.getDictionaryIds().removeIf(o -> o <= 0);
        request.getDictionaryIds().forEach(o -> sysModelDictionaryMapper.insert(SysModelDictionary.builder().modelId(id).dictionaryId(o).build()));
    }

    public SysDataModelExt findById(Integer id) {
        return findById(id, false);
    }

    public SysDataModelExt findById(Integer id, boolean all) {
        final SysDataModel dictionary = sysDataModelMapper.selectById(id);
        ExceptionCast.cast(Objects.isNull(dictionary), SysCode.DATA_MODEL_NOT_EXIST);
        SysDataModelExt result = new SysDataModelExt();
        BeanUtils.copyProperties(dictionary, result);
        if (all) {
            result.setDictionaryIds(sysModelDictionaryMapper.selectList(Wrappers.lambdaQuery(SysModelDictionary.class).eq(SysModelDictionary::getModelId, id))
                    .stream().map(SysModelDictionary::getDictionaryId).collect(Collectors.toSet()));
        }
        return result;
    }

}