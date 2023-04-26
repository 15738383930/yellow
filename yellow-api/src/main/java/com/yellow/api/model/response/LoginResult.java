package com.yellow.api.model.response;

import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.entity.response.ResponseResult;
import com.yellow.common.entity.response.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 登录响应结果模型
 * @author Hao.
 * @version 1.0
 * @since 2022/5/31 9:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ApiModel(description = "登录响应结果模型")
public class LoginResult extends ResponseResult {

    private static final long serialVersionUID = -7839550833571948892L;

    @ApiModelProperty(value="token")
    private String token;

    public LoginResult(ResultCode resultCode, String token) {
        super(resultCode);
        this.token = token;
    }

    public static LoginResult success(String token){
        return new LoginResult(CommonCode.SUCCESS, token);
    }
}
