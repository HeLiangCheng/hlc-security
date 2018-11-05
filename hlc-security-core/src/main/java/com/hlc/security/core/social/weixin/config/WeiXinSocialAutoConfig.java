package com.hlc.security.core.social.weixin.config;

import com.hlc.security.core.social.view.ReturnConnectView;
import com.hlc.security.core.social.weixin.connect.WeiXinConnectionFactory;
import com.hlc.security.core.support.properties.SecurityProperties;
import com.hlc.security.core.support.properties.social.WeiXinProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * Created by Liang on 2018/11/2.
 */
@Configuration
@ConditionalOnProperty(prefix = "hlc.security.social.weixin", name = "app-id")
public class WeiXinSocialAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeiXinProperties weixinpro = securityProperties.getSocial().getWeixin();
        WeiXinConnectionFactory weiXinFactory = new WeiXinConnectionFactory(weixinpro.getProviderId(), weixinpro.getAppId(), weixinpro.getAppSecret());
        return weiXinFactory;
    }

    /**
     * 这里将绑定和解绑的视图使用同一个视图来显示
     * @return
     */
    @Bean({"connect/weixinConnected", "connect/weixinConnect"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView() {
        return new ReturnConnectView();
    }


}
