package com.hlc.security.core.support.properties.social;

import javax.swing.*;

/**
 * Created by Liang on 2018/10/30.
 */
public class QQProperties extends org.springframework.boot.autoconfigure.social.SocialProperties {

    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

}

