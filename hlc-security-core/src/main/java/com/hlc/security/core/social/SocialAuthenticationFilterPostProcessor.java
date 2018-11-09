package com.hlc.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * Created by Liang on 2018/11/9.
 * 社交登录后处理器
 */
public interface SocialAuthenticationFilterPostProcessor {
    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
