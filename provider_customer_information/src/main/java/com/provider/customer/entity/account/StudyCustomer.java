package com.provider.customer.entity.account;

import lombok.Data;

import java.util.Date;
@Data
public class StudyCustomer {
    private String id;

    private String accountId;

    private String studyCode;

    private String name;

    private String nickname;

    private String mobile;

    private Integer sex;

    private String qq;

    private String email;

    private String province;

    private String city;

    private String area;

    private String address;

    private Date birthday;

    private Date createTime;

}