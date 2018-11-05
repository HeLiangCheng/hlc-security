package com.hlc.security.browser.controller;

import com.hlc.security.browser.dto.ResponseDto;
import com.hlc.security.core.social.qq.model.SocialUserInfo;
import com.hlc.security.core.support.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Liang on 2018/10/19.
 */
@RestController
public class BrowserSecurityController {

    private Logger logger = LoggerFactory.getLogger(BrowserSecurityController.class);

    private RequestCache reqCache = new HttpSessionRequestCache();

    private RedirectStrategy strategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties properties;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @RequestMapping("/authenticate/check")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseDto authentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedReq = reqCache.getRequest(request, response);
        if (savedReq != null) {
            String targetUrl = savedReq.getRedirectUrl();
            logger.info("跳转的Url地址为：" + targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                strategy.sendRedirect(request, response, properties.getBrowser().getLoginPage());
            }
        }
        return new ResponseDto("用户的访问需要身份认证，请返回到登录页面");
    }


    @GetMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo socialUser = new SocialUserInfo();
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        socialUser.setProviderId(connection.getKey().getProviderId());
        socialUser.setProviderUserId(connection.getKey().getProviderUserId());
        socialUser.setNickname(connection.getDisplayName());
        socialUser.setHeadImg(connection.getImageUrl());
        return socialUser;
    }

}
