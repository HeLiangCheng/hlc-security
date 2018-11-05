package com.hlc.security.core.social.weixin.connect;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * Created by Liang on 2018/11/1.
 * 这个类的主要功能就是在发请求的时候拼接一些OAuth2协议标准的参数
 */
public class WeiXinOAuth2Template extends OAuth2Template {

    private String clientId;
    private String clientSecret;
    private String accessTokenUrl;

    private Logger log = LoggerFactory.getLogger(getClass());
    public static final String WEIXIN_REFRESH_TOKEN="https://api.weixin.qq.com/sns/oauth2/refresh_token";

    public WeiXinOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    /**
     * 在换取accessToken的时候，标准的参数是client_id和client_secret 而微信在换取accessToken的时候，参数是appid和secret，所以这里为了适配微信改一下参数
     * @param authorizationCode
     * @param redirectUri
     * @param additionalParameters
     * @return
     */
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        StringBuilder sb=new StringBuilder(accessTokenUrl);
        sb.append("?appid="+clientId);
        sb.append("&secret="+clientSecret);
        sb.append("&code="+authorizationCode);
        sb.append("&grant_type=authorization_code");
        sb.append("&redirect_uri=" + redirectUri);
        return getAccessToken(sb.toString());
    }

    private AccessGrant getAccessToken(String accesstoken_path) {
        log.info("请求access_token" + accesstoken_path);
        ResponseEntity<String> response = getRestTemplate().getForEntity(accesstoken_path, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("获取access_token, 响应内容: " + response.getBody());
            JSONObject jobj = JSONObject.parseObject(response.getBody());
            //验证返回数据
            if (jobj.containsValue("errcode") || jobj.containsValue("errmsg")) {
                throw new RuntimeException("获取access_token失败：" + jobj.getString("errmsg"));
            }
            //构建token
            String access_token = jobj.getString("access_token");
            Long expires_in = jobj.getLong("expires_in");
            String refresh_token = jobj.getString("refresh_token");
            String openid = jobj.getString("openid");
            String scope = jobj.getString("scope");
            WeixinAccessGrant accessGrant = new WeixinAccessGrant(access_token, scope, refresh_token, expires_in);
            accessGrant.setOpenId(openid);
            return accessGrant;
        } else {
            throw new RuntimeException("请求" + accesstoken_path + "失败，返回信息：" + response.getBody());
        }
    }

    //构建获取授权码的请求。也就是引导用户跳转到微信的地址
    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid=" + clientId + "&scope=snsapi_login";
        return url;
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthenticateUrl(parameters);
    }

    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {
        // https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
        StringBuilder sb = new StringBuilder(WEIXIN_REFRESH_TOKEN);
        sb.append("?appid=" + clientId);
        sb.append("&grant_type=refresh_token");
        sb.append("&refresh_token=" + refreshToken);
        return getAccessToken(sb.toString());
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        // 添加能处理text/html响应的的Converter
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

}
