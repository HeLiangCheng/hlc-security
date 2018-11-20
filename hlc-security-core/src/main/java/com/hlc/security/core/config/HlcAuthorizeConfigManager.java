package com.hlc.security.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Liang on 2018/11/20.
 * 读取所有的AuthorizeConfigProvider配置信息并完成注册
 */
@Component
public class HlcAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviderList;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        if (authorizeConfigProviderList != null && authorizeConfigProviderList.size() > 0) {
            for (AuthorizeConfigProvider provider : authorizeConfigProviderList) {
                provider.config(config);
            }
        }
    }

}
