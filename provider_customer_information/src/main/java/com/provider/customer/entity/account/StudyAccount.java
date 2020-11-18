package com.provider.customer.entity.account;

import lombok.Data;

import java.util.Date;
@Data
public class StudyAccount {
    private String id;

    private String account;

    private String password;

    private Date createTime;

}