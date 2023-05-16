package com.yellow.common.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 通用请求参数
 * @author zhouhao
 * @date  2021/3/23 11:27
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class RequestData {

    @ApiModelProperty("创建人账号")
    private String createBy;
}
