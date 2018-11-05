package com.hlc.security.browser;

import com.hlc.security.browser.logout.HlcLogoutSuccessHandler;
import com.hlc.security.browser.session.HlcExpiredSessionStrategy;
import com.hlc.security.browser.session.HlcInvalidSessionStrategy;
import com.hlc.security.core.support.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * Created by Liang on 2018/11/5.
 */
@Configuration
public class BrowserSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * session失效时的处理策略配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new HlcInvalidSessionStrategy(securityProperties.getBrowser().getSession().getInvalid());
    }

    /**
     * 并发登录导致前一个session失效时的处理策略配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new HlcExpiredSessionStrategy(securityProperties.getBrowser().getSession().getInvalid());
    }


    /**
     * 退出登录接口
     */
    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new HlcLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
    }

}
