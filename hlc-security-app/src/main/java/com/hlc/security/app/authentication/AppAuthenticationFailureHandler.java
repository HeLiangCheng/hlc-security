package com.hlc.security.app.authentication;


import com.hlc.security.core.constant.LoginType;
import com.hlc.security.core.support.properties.SecurityProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Liang on 2018/10/22.
 * 登录失败处理器
 *  SpringSecurity认证失败基类接口： AuthenticationFailureHandler
 *  SpringSecurity认证默认实现类：   SimpleUrlAuthenticationFailureHandler
 */
@Component("authenticationFailureHandler")
public class AppAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.info("登录失败,原因："+e.getMessage());
        if (LoginType.JSON.equals(securityProperties.getBrowser().getHandlerType())) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(objectMapper.writeValueAsString(e));
        } else {
            super.onAuthenticationFailure(request,response,e);
        }
    }

}
