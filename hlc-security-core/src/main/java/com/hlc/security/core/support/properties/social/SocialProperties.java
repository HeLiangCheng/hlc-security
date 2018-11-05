package com.hlc.security.core.support.properties.social;

/**
 * Created by Liang on 2018/10/30.
 */
public class SocialProperties {

    private String filterProcessesUrl ="/auth";
    private QQProperties qq = new QQProperties();
    private WeiXinProperties weixin = new WeiXinProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public WeiXinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WeiXinProperties weixin) {
        this.weixin = weixin;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

}
