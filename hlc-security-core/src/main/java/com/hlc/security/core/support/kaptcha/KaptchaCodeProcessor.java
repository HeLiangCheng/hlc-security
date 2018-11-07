package com.hlc.security.core.support.kaptcha;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by Liang on 2018/10/27.
 * 模板方法模式封装验证码处理流程
 */
public interface KaptchaCodeProcessor {

    /**
     * 验证码处理：包括验证码生成，存储，发送
     * @param request
     */
    void processor(ServletWebRequest request) throws Exception;

    /**
     * 验证码校验
     */
    void validate(ServletWebRequest request) throws Exception;

}
