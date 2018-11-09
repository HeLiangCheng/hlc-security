package com.hlc.security.core.support.properties.oauth2;

/**
 * Created by Liang on 2018/11/9.
 * OAuth2 属性配置
 */
public class OAuth2Properties {

    private OAuth2ClientProperties[]  clients = {};


    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

}
