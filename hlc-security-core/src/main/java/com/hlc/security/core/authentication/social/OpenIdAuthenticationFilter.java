package com.hlc.security.core.authentication.social;

import com.hlc.security.core.authentication.mobile.SmsCodeAuthenticationToken;
import com.hlc.security.core.constant.SecurityConstant;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Liang on 2018/11/9.
 */
public class OpenIdAuthenticationFilter  extends AbstractAuthenticationProcessingFilter {

    private String openidParam = SecurityConstant.DEFAULT_PARAMETER_NAME_OPEN_ID;
    private String providerIdParam = SecurityConstant.DEFAULT_PARAMETER_NAME_PROVIDER_ID;
    private boolean postOnly = true;

    public OpenIdAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstant.DEFAULT_PROCESSING_OPENID_LOGIN_ACTION, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String openid = this.obtainParameter(request, openidParam);
            String providerId = this.obtainParameter(request, providerIdParam);
            if (openid == null) {
                openid = "";
            }
            if (providerId == null) {
                providerId = "";
            }
            openid = openid.trim();
            providerId = providerId.trim();
            OpenIdAuthenticationToken token = new OpenIdAuthenticationToken(openid, providerId);
            this.setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);
        }
    }


    protected String obtainParameter(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }


    protected void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


    public String getOpenidParam() {
        return openidParam;
    }

    public void setOpenidParam(String openidParam) {
        Assert.hasText(openidParam, "Username parameter must not be empty or null");
        this.openidParam = openidParam;
    }

    public String getProviderIdParam() {
        return providerIdParam;
    }

    public void setProviderIdParam(String providerIdParam) {
        this.providerIdParam = providerIdParam;
    }

    public boolean isPostOnly() {
        return postOnly;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}
