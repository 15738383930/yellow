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

/**
    * 系统数据模型
    */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="系统数据模型")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_data_model")
public class SysDataModel extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="id")
    private Integer id;

    /**
     * 数据模型名称
     */
    @TableField(value = "model_name")
    @ApiModelProperty(value="数据模型名称")
    private String modelName;

    /**
     * 数据模型类型
     */
    @TableField(value = "model_type")
    @ApiModelProperty(value="数据模型类型")
    private String modelType;

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

    /**
     * 是否删除：1-是 0-否
     */
    @TableField(value = "is_del")
    @ApiModelProperty(value="是否删除：1-是 0-否")
    private Integer isDel;
}