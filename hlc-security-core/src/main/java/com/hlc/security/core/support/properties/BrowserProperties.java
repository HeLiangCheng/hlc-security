package com.hlc.security.core.support.properties;

import com.hlc.security.core.constant.LoginType;
import com.hlc.security.core.constant.SecurityConstant;
import sun.security.util.SecurityConstants;

/**
 * Created by Liang on 2018/10/19.
 */
public class BrowserProperties {

    private String loginPage = SecurityConstant.DEFAULT_LOGIN_PAGE;
    private String signOutUrl = SecurityConstant.DEFAULT_SIGNOUT_URL;
    private String signInUrl = SecurityConstant.DEFAULT_SIGNUP_URL;
    private LoginType handlerType = LoginType.JSON;
    private int rememberSeconds = 3600;
    private SessionProperties session = new SessionProperties();

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    public String getSignInUrl() {
        return signInUrl;
    }

    public void setSignInUrl(String signInUrl) {
        this.signInUrl = signInUrl;
    }

    public LoginType getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(LoginType handlerType) {
        this.handlerType = handlerType;
    }

    public int getRememberSeconds() {
        return rememberSeconds;
    }

    public void setRememberSeconds(int rememberSeconds) {
        this.rememberSeconds = rememberSeconds;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

}
