package com.hlc.security.core.social.qq.config;

import com.hlc.security.core.social.qq.connect.QQConnectionFactory;
import com.hlc.security.core.support.properties.SecurityProperties;
import com.hlc.security.core.support.properties.social.QQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * Created by Liang on 2018/10/30.
 */
@Configuration
@ConditionalOnProperty(prefix = "hlc.security.social.qq", name = "app-id")
public class QQSocialAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(), qqProperties.getAppSecret());
    }

}
