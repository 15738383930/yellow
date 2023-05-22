package com.yellow.api.model.request;

import com.yellow.common.entity.request.RequestData;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.*;

/**
 * 查询系统登录日志请求模型
 * 
 * @author Hao.
 * @email 15738383930@163.com
 * @since 2023-05-22 09:15:14
 */
@Data
@ApiModel(value = "查询系统登录日志请求模型")
@EqualsAndHashCode(callSuper = true)
public class QueryLoginLogRequest extends RequestData {

    @ApiModelProperty("用户名")
    private String key;

}
