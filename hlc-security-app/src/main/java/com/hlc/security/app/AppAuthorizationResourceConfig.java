package com.hlc.security.app;

import com.hlc.security.core.authentication.mobile.SmsCodeAuthenticationConfig;
import com.hlc.security.core.constant.SecurityConstant;
import com.hlc.security.core.support.kaptcha.KaptchaValidateFilter;
import com.hlc.security.core.support.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Created by Liang on 2018/11/6.
 * 资源服务器
 */
@Configuration
@EnableResourceServer
public class AppAuthorizationResourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private SmsCodeAuthenticationConfig smsCodeAuthenticationConfig;
    @Autowired
    private SpringSocialConfigurer hlcSocialConfigurer;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(kaptchaValidateFilter(), UsernamePasswordAuthenticationFilter.class)
                //form表单登录
                .formLogin()
                //配置默认登录页
                .loginPage(SecurityConstant.DEFAULT_AUTHENTICATION_ACTION)
                //登录默认处理地址
                .loginProcessingUrl(SecurityConstant.DEFAULT_USERNAMEPASSWORD_LOGIN_ACTION)
                //登录成功和登录失败处理器
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                // 社交登录相关配置
                .and()
                .apply(hlcSocialConfigurer)
                //验证码配置
                .and()
                .apply(smsCodeAuthenticationConfig)
                //请求拦截配置
                .and()
                .authorizeRequests()
                .antMatchers(SecurityConstant.DEFAULT_AUTHENTICATION_ACTION,
                        securityProperties.getBrowser().getLoginPage(),
                        "/auth/**",
                        "/security/kaptcha/**",
                        "/qqLogin/**",
                        "/user/register",
                        securityProperties.getBrowser().getSession().getInvalid(),
                        securityProperties.getBrowser().getSignInUrl(),
                        securityProperties.getBrowser().getSignOutUrl()).permitAll()
                .anyRequest()
                .authenticated()
                //禁用csrf攻击
                .and()
                .csrf()
                .disable();
    }


    @Bean
    public KaptchaValidateFilter kaptchaValidateFilter() throws Exception {
        KaptchaValidateFilter filter = new KaptchaValidateFilter();
        filter.setFailureHandler(authenticationFailureHandler);
        filter.setSecurityProperties(securityProperties);
        filter.afterPropertiesSet();
        return filter;
    }

}
