package com.hlc.security.core.support.properties;

import com.hlc.security.core.support.properties.kaptcha.VerifyCodeProperties;
import com.hlc.security.core.support.properties.oauth2.OAuth2Properties;
import com.hlc.security.core.support.properties.social.SocialProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Liang on 2018/10/19.
 */
@ConfigurationProperties(prefix = "hlc.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();
    private VerifyCodeProperties verifycode = new VerifyCodeProperties();
    private SocialProperties social = new SocialProperties();
    private OAuth2Properties oauth2 = new OAuth2Properties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public VerifyCodeProperties getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(VerifyCodeProperties verifycode) {
        this.verifycode = verifycode;
    }

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
    }

}

