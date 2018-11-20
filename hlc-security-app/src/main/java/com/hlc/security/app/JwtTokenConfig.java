package com.hlc.security.app;

import com.hlc.security.app.jwt.MyJwtTokenEnhancer;
import com.hlc.security.core.support.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Jwt令牌配置
 *  1. 当jerry.security.oauth2.storeType的值是jwt的时候，这个类里面的所有配置都生效
 *  2. 或者没有这个配置项的时候，这个类里面的所有配置都生效  matchIfMissing = true
 */
@Configuration
@ConditionalOnProperty(prefix = "hlc.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
public class JwtTokenConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 做一些Token生成中密钥的配置
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        // 设置签名密钥
        accessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtStr());
        return accessTokenConverter;
    }

    @Bean
    @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
    public TokenEnhancer jwtTokenEnhancer() {
        return new MyJwtTokenEnhancer();
    }

}
