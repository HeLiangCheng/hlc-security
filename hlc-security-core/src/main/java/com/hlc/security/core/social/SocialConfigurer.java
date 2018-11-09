package com.hlc.security.core.social;

import com.hlc.security.core.support.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * Created by Liang on 2018/10/30.
 *  社交配置适配器，配置了操作数据库的Repository
 */
@Configuration
@EnableSocial
@Order(1)
public class SocialConfigurer extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;
    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor authenticationFilterPostProcessor;

    @Bean
    public SpringSocialConfigurer hlcSocialConfigurer() {
        String processesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        HlcSpringSocialConfigurer configurer = new HlcSpringSocialConfigurer(processesUrl);
        //设置我们自己的用户注册页面
        configurer.signupUrl(securityProperties.getBrowser().getSignInUrl());
        configurer.setAuthenticationFilterPostProcessor(authenticationFilterPostProcessor);
        return configurer;
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //第一个参数为数据源，第二个参数是产生Connection的工厂(随着不同的服务提供商，这个会不同)，第三个参数是加密的方式(这里不加密)
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        jdbcUsersConnectionRepository.setTablePrefix("hlc_");
        if(connectionSignUp!=null) {
            jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        }
        return jdbcUsersConnectionRepository;
    }

    /**
     * 帮助我们获取用户社交账号的信息和将信息传递给Spring Social
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator));
    }


}
