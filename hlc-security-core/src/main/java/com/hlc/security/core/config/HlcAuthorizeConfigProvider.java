package com.hlc.security.core.config;

import com.hlc.security.core.constant.SecurityConstant;
import com.hlc.security.core.support.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by Liang on 2018/11/20.
 */
@Component
@Order(Integer.MIN_VALUE)
public class HlcAuthorizeConfigProvider implements AuthorizeConfigProvider{

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                SecurityConstant.DEFAULT_AUTHENTICATION_ACTION,
                securityProperties.getBrowser().getLoginPage(),
                "/auth/**",
                "/security/kaptcha/**",
                "/qqLogin/**",
                "/social/signUp",
                securityProperties.getBrowser().getSession().getInvalid(),
                securityProperties.getBrowser().getSignInUrl(),
                securityProperties.getBrowser().getSignOutUrl()).permitAll();
    }

}
