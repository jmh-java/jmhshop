package com.common.service.impl;

import com.common.service.ContactClientService;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactClientServiceFallbackFactory implements FallbackFactory<ContactClientService> {
    @Override
    public ContactClientService create(Throwable throwable) {
        return new ContactClientService() {
//            @Override
//            public PageInfo<Contact> getList(String name, Integer page, Integer size) {
//                Contact contact = new Contact();
//                List<Contact> list = new ArrayList<>();
//                list.add(contact);
//                PageInfo<Contact> pageInfo = new PageInfo<>(list);
//                return pageInfo;
//            }
//
//            @Override
//            public Boolean addContact(Contact contact) {
//                return null;
//            }
//
//            @Override
//            public Boolean delContact(Integer id) {
//                return null;
//            }
        };
    }
}
