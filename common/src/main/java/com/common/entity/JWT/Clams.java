package com.common.entity.JWT;

import lombok.Data;

import java.util.Date;

@Data
public class Clams {
    //token唯一标识符(staffId)
    private String jti;
    //发布时间
    private Date iat;
    //声明标识JWT之前的时间
    private Date nbf;
    //到期时间
    private Date exp;
    //发行人
    private String iss;
    //受众
    private String aud;
    //主信息
    private StaffDTO sub;

}
