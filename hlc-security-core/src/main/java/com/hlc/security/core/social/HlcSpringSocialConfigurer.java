package com.hlc.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Created by Liang on 2018/10/31.
 */
public class HlcSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public HlcSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    //这里覆盖掉父类的postProcess()方法，个性化自己的过滤器，目的是改变这个过滤器处理的URL
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) super.postProcess(object);
    }

}
