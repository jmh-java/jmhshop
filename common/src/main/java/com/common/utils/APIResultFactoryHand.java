package com.common.utils;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Component;

@Component
public class APIResultFactoryHand {

    public static APIResult result(Integer code, String msg) {
        return new APIResult(code, msg);
    }

    public static <T> APIResult<T> result(Integer code, String msg, T data) {
        return new APIResult(code, msg, data);
    }

    public static APIResult result(ExceptionEnum exceptionEnum) {
        return new APIResult(exceptionEnum.getErrcode(), exceptionEnum.getErrmsg());
    }

    public static APIResult success() {
        return new APIResult(APIResultFactory.CODE_SUCCESS, APIResultFactory.MSG_SUCCESS);
    }

    public static <T> APIResult<T> success(T data) {
        if(data != null && data instanceof Page) {
            Page page = (Page) data;
            if(page.getPageSize() != 0) {
                return new APIResult(APIResultFactory.CODE_SUCCESS, APIResultFactory.MSG_SUCCESS,  page.getResult());
            }
        }
        return new APIResult(APIResultFactory.CODE_SUCCESS, APIResultFactory.MSG_SUCCESS, data);
    }

    public static APIResult error(ExceptionEnum exceptionEnum) {
        return new APIResult(exceptionEnum.getErrcode(), exceptionEnum.getErrmsg());
    }

    public static APIResult error(Integer code, String msg) {
        return new APIResult(code, msg);
    }

    public static APIResult errPrarms() {
        return new APIResult(APIResultFactory.CODE_ERR_PARAMS, APIResultFactory.MSG_ERR_PARAMS);
    }
}
