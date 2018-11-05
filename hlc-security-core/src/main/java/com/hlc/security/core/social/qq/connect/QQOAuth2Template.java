package com.hlc.security.core.social.qq.connect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.Charset;

/**
 * Created by Liang on 2018/11/1.
 * 覆写OAuth2Template，因为OAuth2Template需要返回json,而QQ返回自定义格式
 */
public class QQOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        // 因为在OAuth2Template中的exchangeForAccess换取令牌的时候，只有useParametersForClientAuthentication为true的时候
        // 才会传递client_id和client_secret的值。
        // 所以这里需要设置setUseParametersForClientAuthentication为true，才能在发请求的时候带着client_id和client_secret的值
        setUseParametersForClientAuthentication(true);
    }


    //覆写createRestTemplate方法
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        // 添加能处理text/html响应的的Converter
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }


    /**
      覆写postForAccessGrant方法
     access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseString = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.info("获取accessToken的响应是:" + responseString);
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseString, "&");
        // 令牌、过期时间和刷新令牌
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");
        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

}
