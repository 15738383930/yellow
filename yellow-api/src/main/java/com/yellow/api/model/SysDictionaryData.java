package com.yellow.api.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yellow.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
    * 字典数据
    */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="字典数据")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_dictionary_data")
public class SysDictionaryData extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 字典类型ID
     */
    @NotNull(message = "字典类型ID不能为空！")
    @TableField(value = "dict_type_id")
    @ApiModelProperty(value="字典类型ID")
    private Long dictTypeId;

    /**
     * 字典标签
     */
    @NotEmpty(message = "字典标签不能为空！")
    @TableField(value = "dict_label")
    @ApiModelProperty(value="字典标签")
    private String dictLabel;

    /**
     * 字典值
     */
    @NotEmpty(message = "字典值不能为空！")
    @TableField(value = "dict_value")
    @ApiModelProperty(value="字典值")
    private String dictValue;

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