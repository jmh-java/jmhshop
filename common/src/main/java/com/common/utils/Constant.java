package com.common.utils;

import java.util.Arrays;
import java.util.List;

public class Constant {

    /** 请求成功 */
    public static final Integer CODE_SUCCESS = 200;
    public static final String MSG_SUCCESS = "success";
    /** 请求成功 */
    public static final Integer CODE_PWD_ERROR = 505;
    public static final String MSG_PWD_ERROR = "密码为初始密码,请重新设置密码";
    /** 请求成功 */
    public static final Integer LOGIN_ERR = 401;
    public static final String LOGIN_MSG = "账号或密码错误";
    /** select没有检索到符合的数据 */
    public static final Integer CODE_NODATA= 10005;
    public static final String MSG_NODATA = "没有数据。";
    /** 修改失败 */
    public static final Integer CODE_ERR_SAVE= 10003;
    public static final String MSG_ERR_SAVE = "保存失败。";
    /** 删除失败 */
    public static final Integer CODE_ERR_DEL= 10004;
    public static final String MSG_ERR_DEL = "删除失败。";
    /** 修改失败 */
    public static final Integer CODE_ERR_EDIT= 10006;
    public static final String MSG_ERR_EDIT = "修改失败。";
    /** 数据错误 */
    public static final Integer CODE_ERR_PARAMS= 10007;
    public static final String MSG_ERR_PARAMS = "缺少参数。";
    /** 请求失败 */
    public static final Integer CODE_ERR_ASK= 10008;
    public static final String MSG_ERR_ASK = "请求失败。";

    /*******************七牛本地常量信息 *******************/
    public static final String QINIU_APPKEY = "5J6PV2djAR2IOYX0NhlcOHNsxNJpNxo0NXUlQwS0";
    public static final String QINIU_SECRETKEY = "";
    public static final String QINIU_UPLOAD_MATERIAL = "http://cdn.maijiacloud.cn/";
    public static final String QINIU_UPLOAD_TEMPORARYFACE = "http://facetemplate.maijiacloud.cn/";
    public static final String QINIU_UPLOAD_FACE="http://face.maijiacloud.cn/";
    public static final String QINIU_UPLOAD_MAIJIACRM="http://maijiacrmcdn.maijiacloud.cn/";

    /** 极光推送常量 */
    public static final String JPUSH_KEY = "d7519c65619961620890930a";
    public static final String JPUSH_SECRET = "";
    public static final String LIVE_TIME = "300000";
    public static final Integer LOGING_ERR_CODE = 400;
    public static final String LOGING_ERR_MSG = "用户名或者密码错误";
    public static final Integer LOGING_SUCCESS_CODE = 200;
    public static final String LOGING_SUCCESS_MSG = "登录成功";
    public static final Integer LOGING_JPUSH_CODE = 401;
    public static final String LOGING_JPUSH_MSG = "登录成功，设置极光别名失败";

    public static final double JINLV = 1;
    
    /** 核销机常量 **/
    public static final String KAMO_SESSION_KEY= "d64475894c2e4182ac4b1023f767f2f4";
    //public static final String KM_URL = "http://47.93.125.236";
    public static final String KM_URL = "http://www.kamalyoga.top";
//    public static final String KM_URL = "http://localhost:8096/kamoapi";
    public static final List<String> CMN_SESSION_KEY = Arrays.asList("52eb515219284db9872dff695f820337");
    
    
    /** 消息队列 **/
    public static final String KAMO_GROUP_ID = "GID_PIC_";
}
