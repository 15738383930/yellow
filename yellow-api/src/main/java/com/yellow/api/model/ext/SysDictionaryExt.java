package com.yellow.api.model.ext;

import com.yellow.api.model.SysDictionary;
import com.yellow.api.model.SysDictionaryData;
import com.yellow.api.model.SysMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictionaryExt extends SysDictionary {

    @ApiModelProperty("字典数据")
    private List<SysDictionaryData> data;

    public List<SysDictionaryData> getData() {
        return data == null ? new ArrayList<>() : data;
    }
}