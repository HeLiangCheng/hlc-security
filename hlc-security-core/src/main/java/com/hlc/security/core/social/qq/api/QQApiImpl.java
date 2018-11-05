package com.hlc.security.core.social.qq.api;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * Created by Liang on 2018/10/27.
 */
public class QQApiImpl extends AbstractOAuth2ApiBinding implements QQApi {

    private Logger logger = LoggerFactory.getLogger(getClass());
    //访问用户资料
    private final String QQ_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    //根据access_token获取用户OpenID
    private final String QQ_USER_APP_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    //申请QQ登录成功后，分配给应用的appid
    private String appid;

    //用户的ID，与QQ号码一一对应
    private String openid;

    private ObjectMapper objmapper = new ObjectMapper();

    public QQApiImpl(String accessToken, String appid) {
        super(accessToken, TokenStrategy.OAUTH_TOKEN_PARAMETER);
        this.appid = appid;
        String openurl = String.format(QQ_USER_APP_OPENID, accessToken);
        ResponseEntity<String> openidResult = getRestTemplate().getForEntity(openurl, String.class);
        if (openidResult.getStatusCode() == HttpStatus.OK) {
            String openidbody = openidResult.getBody();
            String openidstr = StringUtils.substringBetween(openidbody,"\"openid\":\"","\"} )");
            logger.info("获取的数据为："+openidbody+",数据中截取的openid="+openidstr);
            this.openid = openidstr;
        } else {
            this.openid = null;
        }
    }

    @Override
    public QQUserInfo getUserInfo() {
        String userinfo_url = String.format(QQ_USER_INFO, appid, openid);
        try {
            ResponseEntity<String> userResult = getRestTemplate().getForEntity(userinfo_url, String.class);
            if (userResult.getStatusCode() == HttpStatus.OK) {
                String userbody = userResult.getBody();
                QQUserInfo userInfo = objmapper.readValue(userbody, QQUserInfo.class);
                userInfo.setOpenId(openid);
                return userInfo;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

}
