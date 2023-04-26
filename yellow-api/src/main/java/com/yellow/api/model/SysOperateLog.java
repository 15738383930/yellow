package com.yellow.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统操作日志表
 *
 * @Author Hao.
 * @Date 2020-06-02 14:10:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysOperateLog implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 操作事件/内容
     */
    private String operateEvent;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作人员
     */
    private String operateName;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 请求URL
     */
    private String operateUrl;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 操作状态（成功/ 失败）
     */
    private String operateStatus;

    /** 请求耗时 */
    private Long time;

    /**
     * 请求参数
     */
    private String params;
}
