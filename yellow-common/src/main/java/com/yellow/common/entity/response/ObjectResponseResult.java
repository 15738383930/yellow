package com.yellow.common.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 对象响应模型<p>
 *     <li>如果result只想返回一个字符串、一个数字等，那么请不要用该模型，还请遵从“规范”，编写一个{例：{@link FileResponseResult}}的响应模型，来提升系统的可维护、可扩展性。</li>
 *     <li>如果result要返回多个属性，那么请【编写/使用】一个【响应模型/数据模型】来存放这些属性，使用方法请参考{@link com.yellow.api.controller.BizInspectionRuleController#get}</li>
 * <br>
 * PS: “规范”后，可维护、可扩展性提高了，而且，如果有swagger的话，文档内容也更丰富、全面。
 * @author Hao.
 * @version 1.0
 * @since 2021/12/14 11:19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("对象响应模型")
public class ObjectResponseResult<T> extends ResponseResult {

    private static final long serialVersionUID = -14445086219556964L;

    @ApiModelProperty("结果")
    T result;

    public ObjectResponseResult(ResultCode resultCode, T result){
        super(resultCode);
       this.result = result;
    }

    public static <T> ObjectResponseResult<T> success(T result){
        return new ObjectResponseResult<>(CommonCode.SUCCESS, result);
    }

    public static <T> ObjectResponseResult<T> get(ResultCode resultCode, T result){
        return new ObjectResponseResult<>(resultCode, result);
    }

    public static <T> ObjectResponseResult<T> fail(T result){
        return new ObjectResponseResult<>(CommonCode.FAIL, result);
    }

    public static ObjectResponseResult<Void> fail(){
        return new ObjectResponseResult<>(CommonCode.FAIL, null);
    }
}
