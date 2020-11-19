package com.provider.customer.service.account.impl;

import com.provider.customer.dao.account.StudyAccountMapper;
import com.provider.customer.entity.account.StudyAccount;
import com.provider.customer.service.account.CustomerOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerOperatorServiceImpl implements CustomerOperatorService {
    @Autowired
    private StudyAccountMapper studyAccountMapper;

    /**
     * 新增账号信息
     * @param studyAccount
     */
    @Override
    public void addStudyAccount(StudyAccount studyAccount) {
        studyAccountMapper.insertSelective(studyAccount);
    }
}
