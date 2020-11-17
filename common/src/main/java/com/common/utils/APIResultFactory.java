package com.common.utils;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class APIResultFactory {

    public static final int CODE_SUCCESS = 0;
    public static final String MSG_SUCCESS = "ok";
    public static final int CODE_ERR_PARAMS = 10001;
    public static final String MSG_ERR_PARAMS = "参数错误";

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
        return new APIResult(CODE_SUCCESS, MSG_SUCCESS);
    }

    public static <T> APIResult<T> success(T data) {
        if(data != null && data instanceof Page) {
            Page page = (Page) data;
            if(page.getPageSize() != 0) {
                Map<String, Object> pageMap = new HashMap<>();
                pageMap.put("pageNum", page.getPageNum());
                pageMap.put("pageSize", page.getPageSize());
                pageMap.put("startRow", page.getStartRow());
                pageMap.put("endRow", page.getEndRow());
                pageMap.put("total", page.getTotal());
                pageMap.put("pages", page.getPages());
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("page", pageMap);
                resultMap.put("data", page.getResult());
                return new APIResult(CODE_SUCCESS, MSG_SUCCESS, resultMap);
            }
        }
        return new APIResult(CODE_SUCCESS, MSG_SUCCESS, data);
    }

    public static APIResult error(ExceptionEnum exceptionEnum) {
        return new APIResult(exceptionEnum.getErrcode(), exceptionEnum.getErrmsg());
    }

    public static APIResult error(Integer code, String msg) {
        return new APIResult(code, msg);
    }

    public static APIResult errPrarms() {
        return new APIResult(CODE_ERR_PARAMS, MSG_ERR_PARAMS);
    }
}
