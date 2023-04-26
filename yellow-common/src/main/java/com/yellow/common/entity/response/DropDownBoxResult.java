package com.yellow.common.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 下拉框响应结果模型
 * @author Hao.
 * @version 1.0
 * @date 2022/3/31 10:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("下拉框响应结果模型")
public class DropDownBoxResult extends ResponseResult {

    private static final long serialVersionUID = -81475066383443361L;

    @ApiModelProperty("下拉框数据列表")
    private List<DropDownBox> dropDownBox;

    public DropDownBoxResult(ResultCode resultCode, List<DropDownBox> dropDownBox){
        super(resultCode);
        this.dropDownBox = dropDownBox;
    }

    public static DropDownBoxResult success(List<DropDownBox> dropDownBox){
        return new DropDownBoxResult(CommonCode.SUCCESS, dropDownBox);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("下拉框数据模型（兼容ElementUI）")
    public static class DropDownBox {

        @ApiModelProperty("后端需要的值")
        private String value;

        @ApiModelProperty("前端展示的值")
        private String label;
    }
}
