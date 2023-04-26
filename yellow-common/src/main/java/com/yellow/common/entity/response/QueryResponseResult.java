package com.yellow.common.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("查询响应模型")
public class QueryResponseResult<T> extends ResponseResult {

    private static final long serialVersionUID = -14445086219556964L;

    @ApiModelProperty("查询结果")
    QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode, QueryResult<T> queryResult){
        super(resultCode);
       this.queryResult = queryResult;
    }

    public static <T> QueryResponseResult<T> success(QueryResult<T> queryResult){
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }

    public static <T> QueryResponseResult<T> success(List<T> list, long total){
        return QueryResponseResult.success(QueryResult.get(list, total));
    }
}
