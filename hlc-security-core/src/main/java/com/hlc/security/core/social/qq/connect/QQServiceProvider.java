package com.hlc.security.core.social.qq.connect;

import com.hlc.security.core.social.qq.api.QQApi;
import com.hlc.security.core.social.qq.api.QQApiImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * Created by Liang on 2018/10/27.
 * QQ第三方登录服务提供商连接器
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQApi> {

    //Authorization Code获取URL  authorizeUrl
    private static final String HLC_QQ_AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";

    //权限自动续期获取Access Token  accessTokenUrl
    private static final String HLC_QQ_ACCESSTOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    private String appId;

    public QQServiceProvider(String appid, String appSecret) {
        //这里先使用默认的OAuth2Template，里面有对令牌、过期时间和刷新令牌正确解析
        super(new QQOAuth2Template(appid, appSecret, HLC_QQ_AUTHORIZE_URL, HLC_QQ_ACCESSTOKEN_URL));
        this.appId = appid;
    }

    @Override
    public QQApi getApi(String accessToken) {
        return new QQApiImpl(accessToken, appId);
    }

}
