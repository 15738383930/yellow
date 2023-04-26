package com.yellow.api.model.request;

import com.yellow.common.entity.request.RequestData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="用户信息", description="用户信息")
public class SysUserRequest extends RequestData {
    /**
     * 账号
     * （可输入的字符【大小写字母，数字，下划线，减号】）
     */
    @NotBlank(message = "账号不能为空！")
    @ApiModelProperty(value = "账号")
//    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,6}$|^[\\dA-Za-z_]{4,12}$", message = "请输入4到12位【字母，数字，下划线】或2到6位【汉字】组成的账号。")
    private String userId;

    /**
     * 密码
     * （可输入的特殊字符集【!.@#$%^&*?】）
     *  前端已加密
     */
    @NotBlank(message = "密码不能为空！")
    @ApiModelProperty(value = "密码")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*?.]{8,16}$",
//            message = "请输入8到16位，且至少包含大写字母、小写字母、数字的密码。")
    private String password;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空！")
    @ApiModelProperty(value = "姓名")
    private String userName;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空！")
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 启用状态（0：停用，1：启用）
     */
    @NotNull(message = "启用状态不能为空！")
    @ApiModelProperty(value = "启用状态（0：停用，1：启用）")
    private Integer enableStatus;

    /**
     * 电子邮箱
     */
    @Email(message = "请输入正确的电子邮箱！")
    @ApiModelProperty(value = "电子邮箱")
    private String email;

    /**
     * 账号角色
     */
    @NotBlank(message = "账号角色不能为空！")
    @ApiModelProperty(value = "账号角色")
    private String roleNos;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;
}