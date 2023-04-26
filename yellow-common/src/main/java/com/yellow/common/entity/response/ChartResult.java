package com.yellow.common.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("图表响应结果模型")
public class ChartResult extends ResponseResult {

    private static final long serialVersionUID = 8061819804644070055L;

    @ApiModelProperty("图表数据模型")
    private List<Chart> chartList;

    public ChartResult(ResultCode resultCode, List<Chart> chartList){
        super(resultCode);
        this.chartList = chartList;
    }

    public static ChartResult success(List<Chart> chartList){
        return new ChartResult(CommonCode.SUCCESS, chartList);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("图表数据模型")
    public static class Chart {

        private String name;

        private int value;
    }
}
