package com.hlc.security.demo.service.impl;

import com.hlc.security.demo.entity.User;
import com.hlc.security.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by Liang on 2018/8/28.
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User validateUserName(String name) {
        System.out.println("开始校验用户名是否已经在数据库中存在：" + name);
        return new User(name, "123456", "test@126.com");
    }

}
