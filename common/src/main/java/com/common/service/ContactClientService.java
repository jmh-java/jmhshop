package com.common.service;

import com.common.service.impl.ContactClientServiceFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "PROVIDER-CONTACT", fallbackFactory = ContactClientServiceFallbackFactory.class)
@Component
public interface ContactClientService {
}
