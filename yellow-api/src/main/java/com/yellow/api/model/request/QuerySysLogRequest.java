package com.yellow.api.model.request;

import com.yellow.common.entity.request.RequestData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("查询系统角色请求模型")
public class QuerySysLogRequest extends RequestData {

    @ApiModelProperty("用户名／用户操作")
    private String key;
}