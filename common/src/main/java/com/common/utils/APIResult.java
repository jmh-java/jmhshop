package com.common.utils;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIResult<T> {

    /** 请求结果代码 */
    public Integer code;

    /** 请求结果文本 */
    public String message;

    /** 请求结果数据 */
    public T data;

    public APIResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public APIResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
