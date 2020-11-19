package com.common.utils.responseResult;

import com.common.utils.ApiException;
import com.common.utils.Constant;
import com.common.utils.ExceptionEnum;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

@Component
public class APIResultFactoryResponse {

    public static APIResultResponse result(Integer code, String msg) {
        return new APIResultResponse(false,code, msg,new ArrayList<>());
    }
    public static APIResultResponse result(Boolean success,Integer code, String msg) {
        return new APIResultResponse(success,code, msg,new ArrayList<>());
    }

    public static <T> APIResultResponse<T> result(Boolean success, Integer code, String msg, T response) {
        return new APIResultResponse(success,code, msg, response);
    }

    public static APIResultResponse result(ExceptionEnum exceptionEnum) {
        return new APIResultResponse(false,exceptionEnum.getErrcode(), exceptionEnum.getErrmsg());
    }

    public static APIResultResponse success() {
        return new APIResultResponse(true, Constant.CODE_SUCCESS, Constant.MSG_SUCCESS,new ArrayList<>());
    }

    public static <T> APIResultResponse<T> success(T data) {
        if(data != null && data instanceof Page) {
            Page page = (Page) data;
            if(page.getPageSize() != 0) {
                PageData pageData=new PageData();
                pageData.setCurrentPage(page.getPageNum());
                pageData.setPageSize(page.getPageSize());
                //pageData.setStartRow(page.getStartRow());
                pageData.setTotalCount(page.getTotal());
                //pageData.setEndRow(page.getEndRow());
                //pageData.setPages(page.getPages());
                if(page.getResult()!=null){
                    pageData.setData(page.getResult());
                }else{
                    pageData.setData(new ArrayList());
                }
                return new APIResultResponse(true,Constant.CODE_SUCCESS, Constant.MSG_SUCCESS, pageData);
            }
        }
        if(data == null){
            return new APIResultResponse(true,Constant.CODE_SUCCESS, Constant.MSG_SUCCESS, new ArrayList<>());
        }
        return new APIResultResponse(true,Constant.CODE_SUCCESS, Constant.MSG_SUCCESS, data);
    }

    public static APIResultResponse error(ExceptionEnum exceptionEnum) {
        return new APIResultResponse(false,exceptionEnum.getErrcode(), exceptionEnum.getErrmsg());
    }

    public static APIResultResponse error(Integer code, String msg) {
        return new APIResultResponse(false,code, msg,new ArrayList<>());
    }

    public static APIResultResponse errPrarms() {
        return new APIResultResponse(false,Constant.CODE_ERR_PARAMS, Constant.MSG_ERR_PARAMS);
    }

    public static void check(BindingResult result) {
        if(result.hasErrors()) throw new ApiException(ExceptionEnum.PARAM_ERROR.getErrcode(), result.getFieldError().getDefaultMessage());
    }

}
