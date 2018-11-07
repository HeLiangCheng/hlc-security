package com.hlc.security.core.support.kaptcha;

import com.hlc.security.core.constant.SecurityConstant;
import com.hlc.security.core.constant.ValidateType;
import com.hlc.security.core.support.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Liang on 2018/10/24.
 * 验证码过滤器，用于校验验证码是否正确等规则。继承的OncePerRequestFilter保证这个过滤器只被调用一次
 */
public class KaptchaValidateFilter extends OncePerRequestFilter implements InitializingBean{

    @Autowired
    private SecurityProperties securityProperties;
    private AuthenticationFailureHandler failureHandler;
    private Map<String,ValidateType> urlmap =new HashMap<String,ValidateType>();
    private AntPathMatcher pathMatcher =new AntPathMatcher();  //在做uri匹配规则发现这个类，根据源码对该类进行分析，它主要用来做类URLs字符串匹配

    @Autowired
    private Map<String,KaptchaCodeProcessor> kaptchaCodeProcessorMap;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //添加图形验证码验证地址
        urlmap.put(SecurityConstant.DEFAULT_USERNAMEPASSWORD_LOGIN_ACTION, ValidateType.IMAGE);
        addUrlToSet(securityProperties.getVerifycode().getImage().getUrl(), ValidateType.IMAGE);
        //添加短信验证码地址
        urlmap.put(SecurityConstant.DEFAULT_MOBILE_LOGIN_ACTION, ValidateType.SMS);
        addUrlToSet(securityProperties.getVerifycode().getSms().getUrl(), ValidateType.SMS);
    }

    private void addUrlToSet(String source, ValidateType type) {
        String[] urlproperties = StringUtils.splitByWholeSeparatorPreserveAllTokens(source, ",");
        for (String item : urlproperties) {
            urlmap.put(item, type);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateType validateType = getValidateType(request);
        if (validateType != null) {
            try {
                //获取校验处理器类型
                String proKey = validateType.toString().toLowerCase() + "KaptchaCodeProcessor";
                if (kaptchaCodeProcessorMap != null && kaptchaCodeProcessorMap.containsKey(proKey)) {
                    KaptchaCodeProcessor kaptchaProcessor = kaptchaCodeProcessorMap.get(proKey);
                    ServletWebRequest webRequest = new ServletWebRequest(request, response);
                    kaptchaProcessor.validate(webRequest);
                } else {
                    failureHandler.onAuthenticationFailure(request, response, new KaptchaException("为查找到适合的验证码处理器"));
                    return;
                }
            } catch (KaptchaException ex) {
                failureHandler.onAuthenticationFailure(request, response, ex);
                return;
            } catch (Exception ex) {
                failureHandler.onAuthenticationFailure(request, response, new KaptchaException(ex.getMessage()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private ValidateType getValidateType(HttpServletRequest request) {
        ValidateType type = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlmap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    type = urlmap.get(url);
                }
            }
        }
        return type;
    }

    public AuthenticationFailureHandler getFailureHandler() {
        return failureHandler;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

}
