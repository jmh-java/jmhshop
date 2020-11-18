package com.provider.customer.dao.account;

import com.provider.customer.entity.account.StudyCustomer;

public interface StudyCustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(StudyCustomer record);

    int insertSelective(StudyCustomer record);

    StudyCustomer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StudyCustomer record);

    int updateByPrimaryKey(StudyCustomer record);
}