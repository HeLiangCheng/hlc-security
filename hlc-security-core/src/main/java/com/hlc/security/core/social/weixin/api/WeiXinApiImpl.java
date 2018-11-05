package com.hlc.security.core.social.weixin.api;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Liang on 2018/11/1.
 */
public class WeiXinApiImpl extends AbstractOAuth2ApiBinding implements WeiXinApi {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final  String WEIXIN_USERINFO_URL="https://api.weixin.qq.com/sns/userinfo?openid=%s";
    private ObjectMapper objectMapper = new ObjectMapper();

    public WeiXinApiImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    /**
     * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，而微信返回的字符集是UTF-8，所以覆盖了原来的方法。
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converterList = super.getMessageConverters();
        converterList.remove(0);
        converterList.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return converterList;
    }

    /**
     * 获取微信用户信息。
     */
    @Override
    public WeiXinUserInfo getUserInfo(String openId) {
        String userinfopath = String.format(WEIXIN_USERINFO_URL, openId);
        ResponseEntity<String> userresp = super.getRestTemplate().getForEntity(userinfopath, String.class);
        if (userresp.getStatusCode() == HttpStatus.OK) {
            String userbody = userresp.getBody();
            try {
                WeiXinUserInfo userInfo = objectMapper.readValue(userbody, WeiXinUserInfo.class);
                return userInfo;
            } catch (IOException ex) {
                logger.info(ex.getMessage());
                return null;
            }
        }
        return null;
    }

}
