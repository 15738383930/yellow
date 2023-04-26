package com.yellow.common.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

/**
 * 方法参数无效响应结果模型
 * @author Hao.
 * @version 1.0
 * @since 2021/12/10 10:49
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("方法参数无效响应结果模型")
public class MethodArgumentNotValidResponseResult extends ResponseResult {

    private static final long serialVersionUID = -81475066383443361L;

    @ApiModelProperty("提示信息")
    private Map<String, String> promptInfo;

    public MethodArgumentNotValidResponseResult(ResultCode resultCode, Map<String, String> promptInfo){
        super(resultCode);
        this.promptInfo = promptInfo;
    }

    public static MethodArgumentNotValidResponseResult get(Map<String, String> promptInfo){
        return new MethodArgumentNotValidResponseResult(CommonCode.REQUEST_PARAMETER_ERROR, promptInfo);
    }

    public static MethodArgumentNotValidResponseResult get(ResultCode resultCode, Map<String, String> promptInfo){
        return new MethodArgumentNotValidResponseResult(resultCode, promptInfo);
    }
}
