package com.yellow.api.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stars.datachange.model.response.DataDictionaryResult;
import com.stars.datachange.module.DataDictionary;
import com.yellow.api.mapper.SysDataModelMapper;
import com.yellow.api.mapper.SysDictionaryDataMapper;
import com.yellow.api.mapper.SysDictionaryMapper;
import com.yellow.api.mapper.SysModelDictionaryMapper;
import com.yellow.api.model.SysDataModel;
import com.yellow.api.model.SysDictionary;
import com.yellow.api.model.SysDictionaryData;
import com.yellow.api.model.SysModelDictionary;
import com.yellow.common.util.RedisUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StarsDataChangeConfig implements DataDictionary {

    @Resource
    private SysDataModelMapper dataModelMapper;

    @Resource
    private SysModelDictionaryMapper modelDictionaryMapper;

    @Resource
    private SysDictionaryMapper dictionaryMapper;

    @Resource
    private SysDictionaryDataMapper dictionaryDataMapper;

    @Resource
    private RedisUtils redisUtils;

    @Override
    @Cacheable(value = "dictionary", key = "#key", sync = true)
    public Set<DataDictionaryResult> dataDictionary(String key) {
        final Set<DataDictionaryResult> result = new HashSet<>();

        final SysDataModel dataModel = dataModelMapper.selectOne(Wrappers.lambdaQuery(SysDataModel.class).eq(SysDataModel::getModelType, key));
        // 数据模型id
        final Integer modelId = dataModel.getId();

        // 数据模型下的字典id
        final List<Long> dictionaryIds = modelDictionaryMapper.selectList(Wrappers.lambdaQuery(SysModelDictionary.class).eq(SysModelDictionary::getModelId, modelId))
                .stream().map(SysModelDictionary::getDictionaryId).collect(Collectors.toList());

        dictionaryIds.forEach(o -> {
            SysDictionary dictionary = dictionaryMapper.selectById(o);
            final DataDictionaryResult data = new DataDictionaryResult();
            data.setName(dictionary.getDictType());
            data.setMaps(new LinkedHashSet<>());

            // 当前字典（字段）的数据
            final List<SysDictionaryData> dictionaryDataList = dictionaryDataMapper.selectList(Wrappers.lambdaQuery(SysDictionaryData.class).eq(SysDictionaryData::getDictTypeId, o));
            dictionaryDataList.forEach(oo -> {
                data.getMaps().add(DataDictionaryResult.Map.builder().code(oo.getDictValue()).value(oo.getDictLabel()).build());
            });
            result.add(data);
        });

        return result;
    }
}
