package com.hlc.security.browser;

import com.hlc.security.browser.authentication.HlcAuthenticationFailureHandler;
import com.hlc.security.browser.authentication.HlcAuthenticationSuccessHandler;
import com.hlc.security.browser.logout.HlcLogoutSuccessHandler;
import com.hlc.security.browser.session.HlcExpiredSessionStrategy;
import com.hlc.security.core.authentication.mobile.SmsCodeAuthenticationConfig;
import com.hlc.security.core.constant.SecurityConstant;
import com.hlc.security.core.support.kaptcha.KaptchaValidateFilter;
import com.hlc.security.core.support.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * Created by Liang on 2018/9/7.
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private HlcAuthenticationSuccessHandler hlcAuthenticationSuccessHandler;
    @Autowired
    private HlcAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SmsCodeAuthenticationConfig smsCodeAuthenticationConfig;
    @Autowired
    private SpringSocialConfigurer hlcSocialConfigurer;
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KaptchaValidateFilter kaptchaValidateFilter() throws Exception {
        KaptchaValidateFilter filter = new KaptchaValidateFilter();
        filter.setFailureHandler(authenticationFailureHandler);
        filter.setSecurityProperties(securityProperties);
        filter.afterPropertiesSet();
        return filter;
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //tokenRepository.setCreateTableOnStartup(true);  //用于第一次执行生成数据库
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.httpBasic()

        /*
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/authentication/form")
                .and()
                .authorizeRequests()
                .antMatchers("/login.html").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
       */

        http.addFilterBefore(kaptchaValidateFilter(), UsernamePasswordAuthenticationFilter.class)
                //form表单登录
                .formLogin()
                //配置默认登录页
                .loginPage(SecurityConstant.DEFAULT_AUTHENTICATION_ACTION)
                //登录默认处理地址
                .loginProcessingUrl(SecurityConstant.DEFAULT_USERNAMEPASSWORD_LOGIN_ACTION)
                //登录成功和登录失败处理器
                .successHandler(hlcAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                // 社交登录相关配置
                .and()
                .apply(hlcSocialConfigurer)
                //记住我功能配置
                .and()
                .rememberMe()
                .tokenRepository(tokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberSeconds())
                .userDetailsService(userDetailsService)
                // session功能
                .and()
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                //.invalidSessionUrl(securityProperties.getBrowser().getSession().getInvalid())      //session 失效请求的URL地址
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximum())     //设置最大的Session数量，即用户在后面登录产生的Session会把前面登录时的Session失效掉
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isPreventslogin()) //设置这个表示当Session数量达到最大数后，会阻值后面的用户进行登录,默认实现执行throw异常 Maximum sessions of 1 for this principal exceeded
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and()
                //退出登录相关的配置
                .and()
                .logout()
                //配置退出系统URL
                .logoutUrl(SecurityConstant.DEFAULT_LOGOUT_ACTION)
                // 下面两个logoutSuccessUrl和logoutSuccessHandler是互斥的，配置一个后另一个就会失效 配置退出 登录后跳转的URL
                // .logoutSuccessUrl("/logout.html")
                .logoutSuccessHandler(logoutSuccessHandler)
                //退出清除cookie
                .deleteCookies("JSESSIONID")
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
                .disable()
                //验证码配置
                .apply(smsCodeAuthenticationConfig);
    }

}
