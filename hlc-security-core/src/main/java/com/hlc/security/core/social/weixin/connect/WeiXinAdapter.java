package com.hlc.security.core.social.weixin.connect;

import com.hlc.security.core.social.weixin.api.WeiXinApi;
import com.hlc.security.core.social.weixin.api.WeiXinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * Created by Liang on 2018/11/2.
 */
public class WeiXinAdapter implements ApiAdapter<WeiXinApi> {

    private String openId;

    public WeiXinAdapter() {
    }

    //因为setConnectionValues()中调用getUserInfo()方法时需要openId，所以要把openId传进来
    public WeiXinAdapter(String openId) {
        this.openId = openId;
    }

    //测试是否连接成功
    @Override
    public boolean test(WeiXinApi weiXinApi) {
        return true;
    }

    @Override
    public void setConnectionValues(WeiXinApi weiXinApi, ConnectionValues connectionValues) {
        WeiXinUserInfo userInfo = weiXinApi.getUserInfo(openId);
        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setProviderUserId(userInfo.getOpenid());
        connectionValues.setImageUrl(userInfo.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(WeiXinApi weiXinApi) {
        return null;
    }

    @Override
    public void updateStatus(WeiXinApi weiXinApi, String s) {

    }

}
