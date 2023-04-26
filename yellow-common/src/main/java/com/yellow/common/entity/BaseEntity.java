package com.yellow.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基本数据模型<p>
 *     <li>提供基本属性</li>
 *     <li>提供基本属性的动态赋值{@link com.yellow.common.config.MybatisPlusConfig}</li>
 *     PS:基于mybatis-plus
 * @author Hao.
 * @version 1.0
 * @date 2022/4/6 17:22
 */
@Data
public class BaseEntity implements Serializable {

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改人")
    private String updateBy;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除 1-是 0-否")
    private Integer isDel;

    private static final long serialVersionUID = 1L;
}
