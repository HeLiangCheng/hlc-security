package com.hlc.security.core.social.weixin.api;

/**
 * Created by Liang on 2018/11/1.
 */
public class WeiXinUserInfo {

    //普通用户的标识，对当前开发者帐号唯一
    private String openid;
    //普通用户昵称
    private String nickname;
    //普通用户性别，1为男性，2为女性
    private int sex;
    //语言
    private String language;
    //普通用户个人资料填写的省份
    private String province;
    //普通用户个人资料填写的城市
    private String city;
    //国家，如中国为CN
    private String country;
    //用户头像
    private String headimgurl;
    // 用户特权信息
    private String[] privilege;
    //用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
    private String unionid;


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}

