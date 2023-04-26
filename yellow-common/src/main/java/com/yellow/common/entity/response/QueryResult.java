package com.yellow.common.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询结果
 * @author zhouhao
 * @date  2021/3/23 11:29
 */
@Data
@ToString
@ApiModel("查询结果")
public class QueryResult<T> implements Serializable {

    private static final long serialVersionUID = 4587884291701273037L;
    @ApiModelProperty("数据列表")
    private List<T> list;

    @ApiModelProperty("数据总数")
    private long total;

    private QueryResult(List<T> list, long total) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
        this.total = total;
    }

    public static <T> QueryResult<T> get(List<T> list, long total) {
        return new QueryResult<>(list, total);
    }

}
