package com.hlc.security.core.support.properties.social;

/**
 * Created by Liang on 2018/11/2.
 */
public class WeiXinProperties extends org.springframework.boot.autoconfigure.social.SocialProperties {

    private String providerId = "weixin";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

}
