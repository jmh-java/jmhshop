package com.common.utils;
import com.common.utils.exception.JmhShopException;
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int errcode;

    public ApiException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getErrmsg());
        this.errcode = exceptionEnum.getErrcode();
    }

    public ApiException(int errcode, String errmsg) {
        super(errmsg);
        this.errcode = errcode;
    }
    public ApiException(ExceptionEnum exceptionEnum, Object[] formats) {
        super(String.format(exceptionEnum.getErrmsg(), formats));
        this.errcode = exceptionEnum.getErrcode();
    }

    public ApiException(JmhShopException exception) {
        super(exception.getErrmsg());
        this.errcode = exception.getErrcode();
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

}
