package com.xgl.study.spring.service.impl;

import com.xgl.study.spring.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Override
    public void test() {

        System.out.println("user service test ()");

    }
}
