package com.common.utils.responseResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIResultResponse<T> {

    public Integer code;

    public String msg;

    public T response;

    public Boolean success;

    public APIResultResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public APIResultResponse(Boolean success,Integer code, String msg ) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }
    public APIResultResponse( Integer code, String msg, T response) {
        this.code = code;
        this.msg = msg;
        this.response = response;
    }

    public APIResultResponse(Boolean success, Integer code, String msg, T response) {
        this.code = code;
        this.msg = msg;
        this.response = response;
        this.success=success;
    }

}
