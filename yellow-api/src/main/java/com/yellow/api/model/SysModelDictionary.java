package com.yellow.api.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 数据模型与系统字典关联表
    */
@ApiModel(value="数据模型与系统字典关联表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_model_dictionary")
public class SysModelDictionary {
    /**
     * 数据模型id
     */
    @ApiModelProperty(value="数据模型id")
    private Integer modelId;

    /**
     * 字典id
     */
    @ApiModelProperty(value="字典id")
    private Long dictionaryId;
}