package com.common.utils;

public enum ExceptionEnum {
    TOKEN_ERR(5107,"token过期！"),
    TOKEN_MISS(5180,"未查到token！"),
    TOKEN_DEFAULT(5108,"解析token失败，或解析出的token没有主体！"),
    SIGN_ERROR(1100, "秘钥加密失败，缺失加密参数"),
    PARAM_LESS(4009, "缺少参数"),
    CLIENT_NOT_EMPTY(4003, "品牌ID不能为空"),
    STORE_NOT_EMPTY(4002, "门店ID不能为空"),
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
