package com.hlc.security.core.social.weixin.connect;

import com.hlc.security.core.social.weixin.api.WeiXinApi;
import com.hlc.security.core.social.weixin.api.WeiXinApiImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Created by Liang on 2018/11/1.
 */
public class WeiXinServiceProvider extends AbstractOAuth2ServiceProvider<WeiXinApi> {

    //微信获取授权码的url
    private static final String WEIXIN_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect";
    //微信获取accessToken的url
    private static final String WEIXIN_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public WeiXinServiceProvider(String appId, String appSecret) {
        super(new WeiXinOAuth2Template(appId, appSecret, WEIXIN_AUTHORIZE_URL, WEIXIN_ACCESSTOKEN_URL));
    }


    @Override
    public WeiXinApi getApi(String accessToken) {
        return new WeiXinApiImpl(accessToken);
    }

}
