package com.hlc.security.core.social.weixin.connect;

import org.springframework.social.oauth2.AccessGrant;

/**
 * Created by Liang on 2018/11/2.
 * 因为微信和标准的AccessGrant不一样，多了一个openId，这里需要覆盖AccessGrant，添加openId
 */
public class WeixinAccessGrant extends AccessGrant {

    private String openId;

    public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

}
