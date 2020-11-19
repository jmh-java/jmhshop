package com.common.utils.responseResult;

import lombok.Data;


@Data
public class Result<T> {
    /** 请求结果数据 */
    public T data;
    public Result( T data) {
        this.data = data;
    }
}
