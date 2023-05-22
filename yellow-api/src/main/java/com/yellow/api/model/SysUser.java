package com.yellow.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yellow.api.model.valid.AddGroup;
import com.yellow.api.model.valid.EditGroup;
import com.yellow.common.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
    * 系统用户表
    */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends BaseEntity {
    private Integer id;

    /**
    * 用户名
    */
    @NotBlank(message = "账号不能为空！", groups = {AddGroup.class, EditGroup.class})
    @ApiModelProperty(value = "账号")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,6}$|^[\\dA-Za-z_]{4,12}$", message = "请输入4到12位【字母，数字，下划线】或2到6位【汉字】组成的账号。", groups = AddGroup.class)
    private String username;

    /**
    * 密码
    */
    @JsonIgnore
    @NotBlank(message = "密码不能为空！", groups = AddGroup.class)
    @ApiModelProperty(value = "密码")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*?.]{8,16}$", message = "请输入8到16位，且至少包含大写字母、小写字母、数字的密码。", groups = AddGroup.class)
    private String password;

    /**
    * 用户昵称
    */
    @NotBlank(message = "姓名不能为空！", groups = {AddGroup.class, EditGroup.class})
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
    * 手机号（留作备用，后期可延伸手机号登录业务）
    */
    private String phone;

    /**
    * 最后一次登录系统时间
    */
    private Date lastLoginTime;

    /**
    * 用户状态：1-启用 2-禁用
    */
    private Integer status;

    /**
    * 备注
    */
    private String remark;

    @ApiModelProperty(value = "是否删除 1-是 0-否")
    private Integer isDel;
}