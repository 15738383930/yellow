package com.yellow.api.model.request;

import com.yellow.common.entity.request.RequestData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("查询系统用户请求模型")
public class QuerySysUserRequest extends RequestData {

    @ApiModelProperty("用户名")
    private String username;
}