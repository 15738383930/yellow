package com.yellow.api.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SysDetailLog implements Serializable {
    /**
     * 日志id
     */
    private Integer logId;

    /**
     * 日志类型：
     * 1-登录日志
     * 2-操作日志
     */
    private Integer logType;

    /**
     * 日志详情
     */
    private String detail;

    private static final long serialVersionUID = 1L;
}