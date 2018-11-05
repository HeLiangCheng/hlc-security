package com.hlc.security.core.social.qq.connect;

import com.hlc.security.core.social.qq.api.QQApi;
import com.hlc.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * Created by Liang on 2018/10/30.
 */
public class QQAdapter implements ApiAdapter<QQApi> {

    @Override
    public boolean test(QQApi qqApi) {
        return true;
    }

    @Override
    public void setConnectionValues(QQApi qqApi, ConnectionValues values) {
        QQUserInfo userInfo = qqApi.getUserInfo();
        //将用户信息存储到Connection中
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());
    }

    // 绑定解绑的时候使用
    @Override
    public UserProfile fetchUserProfile(QQApi qqApi) {
        return null;
    }

    // 更新个人状态(QQ没有这个接口)
    @Override
    public void updateStatus(QQApi qqApi, String s) {

    }
}
