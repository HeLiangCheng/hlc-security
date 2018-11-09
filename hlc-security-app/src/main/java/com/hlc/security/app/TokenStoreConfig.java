package com.hlc.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Created by Liang on 2018/11/9.
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnProperty(prefix = "hlc.security.oauth2", name = "storeType", havingValue = "redis")
    public TokenStore redisTokenStore() {
        // 从Redis连接工厂拿到Redis连接，就连接到了Redis服务器上，如果产生Token就存在指定的Redis中了
        return new RedisTokenStore(redisConnectionFactory);
    }

}

