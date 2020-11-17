package com.common.utils;

public enum ExceptionEnum {
    TOKEN_ERR(5107,"token过期！"),
    TOKEN_MISS(5180,"未查到token！"),
    TOKEN_DEFAULT(5108,"解析token失败，或解析出的token没有主体！"),
    ;

    private int errcode;
    private String errmsg;

    private ExceptionEnum(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

}
