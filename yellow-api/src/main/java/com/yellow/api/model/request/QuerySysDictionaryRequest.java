package com.yellow.api.model.request;

import com.yellow.common.entity.request.RequestData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("查询系统字典请求模型")
public class QuerySysDictionaryRequest extends RequestData {

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("模型名称")
    private String modelName;
}