package com.yellow.api.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yellow.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
    * 字典类型
    */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="字典类型")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_dictionary")
public class SysDictionary extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 字典类型
     */
    @NotEmpty(message = "字典类型不能为空！")
    @TableField(value = "dict_type")
    @ApiModelProperty(value="字典类型")
    private String dictType;

    /**
     * 字典名称
     */
    @NotEmpty(message = "字典名称不能为空！")
    @TableField(value = "dict_name")
    @ApiModelProperty(value="字典名称")
    private String dictName;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Integer sort;
}