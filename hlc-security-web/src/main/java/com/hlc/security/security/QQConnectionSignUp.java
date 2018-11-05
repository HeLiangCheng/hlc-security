package com.hlc.security.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * Created by Liang on 2018/11/1.
 */
@Component
public class QQConnectionSignUp implements ConnectionSignUp {

    @Override
    public String execute(Connection<?> connection) {
        //根据connection中信息自动创建用户
        return connection.getDisplayName();
    }

}
