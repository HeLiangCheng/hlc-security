package com.hlc.security.core.social.qq.connect;

import com.hlc.security.core.social.qq.api.QQApi;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * Created by Liang on 2018/10/30.
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQApi> {

    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }

}
