package com.hlc.security.app;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * Created by Liang on 2018/11/9.
 * 社交登录注册工具类
 */
@Component
public class AppSignUpUtils {

    public static final String APP_PREFIX = "app_connect_";
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;


    /**
     * 保存社交用户信息到Redis中
     */
    public void saveConnectionData(WebRequest request, ConnectionData connectionData) {
        String key = getAppKey(request);
        redisTemplate.opsForValue().set(key, connectionData, 10, TimeUnit.MINUTES);
    }

    /**
     * 用户注册处理
     * @param request
     * @param userId
     */
    public void doPostSignUp(WebRequest request, String userId) {
        String appkey = getAppKey(request);
        if (!redisTemplate.hasKey(appkey)) {
            throw new RuntimeException("未能找到该第三方社交用户信息");
        }
        ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(appkey);
        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);
        usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);
        redisTemplate.delete(appkey);
    }

    public String getAppKey(WebRequest request) {
        String str = request.getParameter("deviceId");
        if (StringUtils.isEmpty(str)) {
            throw new RuntimeException("未找到设备Id");
        }
        return APP_PREFIX + str;
    }


}
