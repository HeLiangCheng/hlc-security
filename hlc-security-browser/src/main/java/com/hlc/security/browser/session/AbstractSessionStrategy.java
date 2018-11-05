package com.hlc.security.browser.session;

import com.hlc.security.browser.dto.ResponseDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Liang on 2018/11/5.
 */
public class AbstractSessionStrategy {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //跳转的URL地址
    private String destinationUrl;
    /**
     * 跳转前是否创建新的session
     */
    private boolean createNewSession = true;

    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private ObjectMapper objectMapper = new ObjectMapper();


    public AbstractSessionStrategy(String invalidSessionUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }

    //session失效
    protected void onSessionInvalid(HttpServletRequest request,HttpServletResponse response) throws IOException {
        logger.info("session失效");
        if(createNewSession){
            request.getSession();
        }
        String targetUrl=null;
        //判断session失效的返回
        if(request.getRequestURI().endsWith(".html")){
            targetUrl = destinationUrl + ".html";
            logger.info("跳转到" + targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }else{
            String message = "session已失效";
            if (isConcurrency()) {
                message = message + "，有可能是并发登录导致的";
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new ResponseDto(message)));
        }
    }


    /**
     * session失效是否是并发导致的
     * @return
     */
    protected boolean isConcurrency() {
        return false;
    }

}
