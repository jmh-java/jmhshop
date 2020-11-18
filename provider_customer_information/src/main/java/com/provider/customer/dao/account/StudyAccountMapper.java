package com.provider.customer.dao.account;

import com.provider.customer.entity.account.StudyAccount;

public interface StudyAccountMapper {
    int deleteByPrimaryKey(String id);

    int insert(StudyAccount record);

    int insertSelective(StudyAccount record);

    StudyAccount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StudyAccount record);

    int updateByPrimaryKey(StudyAccount record);
}