package com.hlc.security.core.support.properties.kaptcha;

/**
 * Created by Liang on 2018/10/24.
 */
public class KaptchaProperties {
    private String length = "4";
    private String expire = "30";
    private String url ="";     //用于拦截多个URL，进行验证码验证

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
