package com.yellow.common.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 文件响应结果模型
 * @author Hao.
 * @version 1.0
 * @since 2021/12/10 10:49
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("文件响应结果模型")
public class FileResponseResult extends ResponseResult {

    private static final long serialVersionUID = -81475066383443361L;

    @ApiModelProperty("文件网址")
    private String fileUrl;

    public FileResponseResult(ResultCode resultCode, String fileUrl){
        super(resultCode);
        this.fileUrl = fileUrl;
    }

    public static FileResponseResult success(String fileUrl){
        return new FileResponseResult(CommonCode.SUCCESS, fileUrl);
    }
}
