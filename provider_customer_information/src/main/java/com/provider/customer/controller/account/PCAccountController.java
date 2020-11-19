package com.provider.customer.controller.account;

import com.common.utils.responseResult.APIResultFactoryResponse;
import com.common.utils.responseResult.APIResultResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.provider.customer.entity.account.StudyAccount;
import com.provider.customer.service.account.CustomerOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping("/pc")
public class PCAccountController {
    @Autowired
    private CustomerOperatorService customerOperatorService;

    /**
     * 注册账号
     **/
    @PostMapping("/account/addAccount")
    @HystrixCommand(fallbackMethod = "addAccountException")
    public APIResultResponse addAccount(@RequestBody StudyAccount studyAccount) {
        customerOperatorService.addStudyAccount(studyAccount);
        return APIResultFactoryResponse.success();
    }

    public APIResultResponse addAccountException(@RequestBody StudyAccount studyAccount) {
        return APIResultFactoryResponse.success();
    }
}
