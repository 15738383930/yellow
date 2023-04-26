package com.yellow.common.entity.response;

import com.alibaba.fastjson.JSON;
import com.yellow.common.constant.Constants;
import com.yellow.common.exception.ExceptionCast;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Objects;

/**
 * 响应结果
 * @author zhouhao
 * @date  2021/4/12 15:25
 */
@Slf4j
@Data
@ToString
@NoArgsConstructor
@ApiModel("响应模型")
@XmlRootElement
public class ResponseResult implements Response {

    private static final long serialVersionUID = 6980492283837556820L;

    @ApiModelProperty("操作是否成功")
    boolean success = Response.SUCCESS;

    @ApiModelProperty("操作代码")
    int code = SUCCESS_CODE;

    @ApiModelProperty("提示信息")
    String message;

    public ResponseResult(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    @Deprecated
    public ResponseResult(ResultCode resultCode, String msg){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = msg;
    }

    /**
     * 获取响应结果
     * @param resultCode 响应代码
     * @return ResponseResult
     * @author zhouhao
     * @date  2021/4/12 15:33
     */
    public static ResponseResult get(ResultCode resultCode){
        return new ResponseResult(resultCode);
    }

    /**
     * 获取响应结果（非特殊情况，不建议使用此方法）<p>
     * ps: 当你觉得msg的含义等同于resultCode中的message时，才可使用
     * @param resultCode 响应代码
     * @param msg 提示信息（替换响应代码中的提示信息）
     * @return ResponseResult
     * @author zhouhao
     * @date  2021/4/12 15:33
     */
    @Deprecated
    public static ResponseResult get(ResultCode resultCode, String msg){
        return new ResponseResult(resultCode, msg);
    }

    public static ResponseResult success(){
        return get(CommonCode.SUCCESS);
    }

    public static ResponseResult fail(){
        return get(CommonCode.FAIL);
    }

    /**
     * 通过HttpServletResponse的方式输出给客户端
     * @param response
     * @param result
     * @return void
     * @author zhouhao
     * @date  2021/4/19 16:14
     */
    public static void output(HttpServletResponse response, ResponseResult result) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding(Constants.UTF8);
        response.setContentType("application/json; charset=utf-8");
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            log.error("IOException：{}, response message:{}", e.getMessage(), result);
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 通过HttpServletResponse的方式输出给客户端
     * @param response
     * @param resultCode
     * @return void
     * @author zhouhao
     * @date  2021/4/19 16:14
     */
    public static void output(HttpServletResponse response, ResultCode resultCode) {
        output(response, ResponseResult.get(resultCode));
    }

    /**
     * 通过HttpServletResponse的方式输出给客户端
     * @param resultCode
     * @return void
     * @author zhouhao
     * @date  2021/4/19 16:14
     */
    public static void output(ResultCode resultCode) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) getRequestAttributesSafely();
        if(Objects.isNull(servletRequestAttributes)){
            return;
        }
        HttpServletResponse response = servletRequestAttributes.getResponse();
        output(response, ResponseResult.get(resultCode));
    }

    public static RequestAttributes getRequestAttributesSafely(){
        RequestAttributes context;
        try{
            context = RequestContextHolder.currentRequestAttributes();
        }catch (IllegalStateException e){
            context = null;
        }
        return context;
    }

}
