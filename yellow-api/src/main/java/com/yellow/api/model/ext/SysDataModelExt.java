package com.yellow.api.model.ext;

import com.yellow.api.model.SysDataModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDataModelExt extends SysDataModel {

    @ApiModelProperty("字典")
    private Set<Long> dictionaryIds;
}