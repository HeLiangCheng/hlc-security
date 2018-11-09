package com.hlc.security.core.support.properties.oauth2;

/**
 * Created by Liang on 2018/11/9.
 * OAuth2 客户端数据配置
 */
public class OAuth2ClientProperties {

    private String clientId;
    private String clientSecret;
    /**
     * 默认令牌的有效期
     */
    private int tokenValiditySeconds = 7200;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getTokenValiditySeconds() {
        return tokenValiditySeconds;
    }

    public void setTokenValiditySeconds(int tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }
}
