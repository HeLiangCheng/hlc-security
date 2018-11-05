package com.hlc.security.demo.service;

import com.hlc.security.demo.entity.User;

/**
 * Created by Liang on 2018/8/28.
 */
public interface UserService {

    User validateUserName(String name);

}
