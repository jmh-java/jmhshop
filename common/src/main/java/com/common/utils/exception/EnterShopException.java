package com.common.utils.exception;

public enum EnterShopException implements MJException {
    CONDITION_CANNOT_BLANK(6001, "搜索条件不能为空"),
    ;

    private int errcode;
    private String errmsg;

    private EnterShopException(int errcode, String errmsg) {
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
