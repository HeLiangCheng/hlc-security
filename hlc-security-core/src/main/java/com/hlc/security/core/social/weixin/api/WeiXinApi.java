package com.hlc.security.core.social.weixin.api;

/**
 * Created by Liang on 2018/11/1.
 */
public interface WeiXinApi {
    WeiXinUserInfo getUserInfo(String openId);
}
