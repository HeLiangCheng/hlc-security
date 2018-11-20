package com.hlc.security.app;

import com.hlc.security.core.support.properties.SecurityProperties;
import com.hlc.security.core.support.properties.oauth2.OAuth2ClientProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liang on 2018/11/6.
 * 认证服务器
 * 当继承 AuthorizationServerConfigurerAdapter 时，认证服务器将读取用户配置的配置信息
 */
@Configuration
@EnableAuthorizationServer
public class AppAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private TokenStore tokenStore;

    /**
     * 做一些Token生成中密钥的配置
     */
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 对Token做增强信息用的
     */
    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    /**
     * 配置TokenEndpoint
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore)  // 指定Token的存储位置
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);

        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();

            // 将密钥设置和增强信息设置加入集合
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(enhancers);
            endpoints.tokenEnhancer(tokenEnhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }



    /**
     * 与客户端相关的配置，也就是会有哪些客户端回来访问我们的第三方应用，我们会给哪些第三方应用发Token
     * 这个方法配置后，在application.properties中配置的client_id和client_secret就不起作用了
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 指定client信息存储在内存中
        // 因为这里是在我们自己的服务和我们自己的app或前端进行通讯，我们这里就存储在内存中即可
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();

        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
            for (OAuth2ClientProperties config : securityProperties.getOauth2().getClients()) {
                // 循环读取出数组中存放的OAuth2ClientProperties，全部添加到clients中
                 builder.withClient(config.getClientId())
                        .secret(config.getClientSecret())
                        // 令牌的有效时间（单位是秒）
                        .accessTokenValiditySeconds(config.getTokenValiditySeconds())
                        // 设置刷新Token的有效期为一个月
                        .refreshTokenValiditySeconds(2592000)
                        // 配置支持的授权模式
                        .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                        // 发出去的权限有哪些
                        .scopes("all", "read", "write");
            }
        }
    }

}
