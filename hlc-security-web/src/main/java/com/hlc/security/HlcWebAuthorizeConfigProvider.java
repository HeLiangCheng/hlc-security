package com.hlc.security;

import com.hlc.security.core.config.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by Liang on 2018/11/20.
 */
@Component
@Order(Integer.MAX_VALUE)
public class HlcWebAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        // 任何请求都要走这个规则

        //config.anyRequest().hasRole("ADMIN");

        config.anyRequest().access("@rbacService.hasPermission(request, authentication)");

    }

}
