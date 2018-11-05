package com.hlc.security.core.support.properties;

import com.hlc.security.core.constant.SecurityConstant;

/**
 * Created by Liang on 2018/11/5.
 * session 配置信息
 */
public class SessionProperties {

    //session 过期页面配置
    private String invalid = SecurityConstant.DEFAULT_SESSION_INVALID;
    //最大的Session数量
    private int maximum = 1;
    //当Session数量达到最大数后，会阻值后面的用户进行登录
    private boolean preventslogin = false;

    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public boolean isPreventslogin() {
        return preventslogin;
    }

    public void setPreventslogin(boolean preventslogin) {
        this.preventslogin = preventslogin;
    }

}
