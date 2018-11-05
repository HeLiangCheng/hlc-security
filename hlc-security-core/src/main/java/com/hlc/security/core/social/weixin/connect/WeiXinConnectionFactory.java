package com.hlc.security.core.social.weixin.connect;

import com.hlc.security.core.social.qq.api.QQApi;
import com.hlc.security.core.social.weixin.api.WeiXinApi;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * Created by Liang on 2018/11/2.
 */
public class WeiXinConnectionFactory extends OAuth2ConnectionFactory<WeiXinApi> {

    public WeiXinConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WeiXinServiceProvider(appId, appSecret), new WeiXinAdapter());
    }

    //由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可, 不用像QQ那样通过QQAdapter来获取
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if (accessGrant instanceof WeixinAccessGrant) {
            WeixinAccessGrant grant = (WeixinAccessGrant) accessGrant;
            return grant.getOpenId();
        }
        return null;
    }

    @Override
    public Connection<WeiXinApi> createConnection(AccessGrant accessGrant) {
        // 创建OAuth2Connection的时候在父类的实现中，最后一个参数getApiAdapter拿到的始终都是同一个Adapter
        // 但是根据用户返回的AccessToken中的openId应该是每一个openId对应一个自己的Adapter
        // 不像QQ每次传回去的都是同一个Adapter实例，因为QQAdapter中没有openId这个成员变量
        return new OAuth2Connection<>(getProviderId(), extractProviderUserId(accessGrant),
                accessGrant.getAccessToken(), accessGrant.getRefreshToken(), accessGrant.getExpireTime(),
                getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    private ApiAdapter<WeiXinApi> getApiAdapter(String providerUserId) {
        return new WeiXinAdapter(providerUserId);
    }

    private OAuth2ServiceProvider<WeiXinApi> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WeiXinApi>) getServiceProvider();
    }

}
