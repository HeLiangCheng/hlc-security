package com.hlc.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlc.security.browser.dto.ResponseDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Liang on 2018/11/5.
 */
public class HlcLogoutSuccessHandler implements LogoutSuccessHandler {

    Logger log = LoggerFactory.getLogger(getClass());
    private String signOutUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public HlcLogoutSuccessHandler(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("退出登录");
        if (StringUtils.isBlank(signOutUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new ResponseDto("退出成功")));
        } else {
            response.sendRedirect(signOutUrl);
        }
    }

}
