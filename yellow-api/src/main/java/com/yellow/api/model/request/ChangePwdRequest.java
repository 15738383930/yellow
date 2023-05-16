package com.yellow.api.model.request;

import com.yellow.common.entity.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 修改密码请求参数
 * @author zhouhao
 * @date  2021/4/8 9:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChangePwdRequest extends RequestData {

    @NotBlank(message = "旧密码不能为空！")
    @ApiModelProperty(value = "旧密码", required = true)
    String password;

    @NotBlank(message = "新密码不能为空！")
    @ApiModelProperty(value = "新密码", required = true)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*?.]{8,16}$",
            message = "请输入8到16位（大写字母，小写字母，数字，!.@#$%^&*?）且至少包含大写字母、小写字母、数字的密码")
    String newPassword;
}
