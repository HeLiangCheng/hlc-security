package com.hlc.security.browser.repository;

import com.hlc.security.core.constant.ValidateType;
import com.hlc.security.core.support.kaptcha.KaptchaCode;
import com.hlc.security.core.support.kaptcha.KaptchaRepository;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by Liang on 2018/11/7.
 * APP的中不存在Session 所以将验证码存储在redis中
 */
@Component
public class SessionKaptchaRepository implements KaptchaRepository {

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

    @Override
    public void save(ServletWebRequest request, KaptchaCode code, ValidateType validateType) {
        sessionStrategy.setAttribute(request, getSessionKey(validateType), code);
    }

    @Override
    public KaptchaCode get(ServletWebRequest request, ValidateType validateType) {
        return (KaptchaCode) sessionStrategy.getAttribute(request, getSessionKey(validateType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateType validateType) {
        sessionStrategy.removeAttribute(request, getSessionKey(validateType));
    }

    /**
     * 构建验证码放入session时的key
     * @param type
     * @return
     */
    private String getSessionKey(ValidateType type) {
        return SESSION_KEY_PREFIX + type.toString().toUpperCase();
    }

}
