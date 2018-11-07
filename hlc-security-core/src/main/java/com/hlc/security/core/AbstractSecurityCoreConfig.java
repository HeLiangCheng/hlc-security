package com.hlc.security.core;

import com.hlc.security.core.constant.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Created by Liang on 2018/11/6.
 * 该配置文件配置App与Browse共同的配置项目
 */
public class AbstractSecurityCoreConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    //核心配置
    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.formLogin()   //form表单登录
                //配置默认登录页
                .loginPage(SecurityConstant.DEFAULT_AUTHENTICATION_ACTION)
                //登录默认处理地址
                .loginProcessingUrl(SecurityConstant.DEFAULT_USERNAMEPASSWORD_LOGIN_ACTION)
                //登录成功和登录失败处理器
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
    }

}
