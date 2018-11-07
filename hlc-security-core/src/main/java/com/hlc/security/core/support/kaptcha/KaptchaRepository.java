package com.hlc.security.core.support.kaptcha;

import com.hlc.security.core.constant.ValidateType;
import com.hlc.security.core.support.kaptcha.KaptchaCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by Liang on 2018/11/7.
 */
public interface KaptchaRepository {

    public static final String SESSION_KEY_PREFIX = "hlc_security_kaptcha_";

    /**
     * 保存验证码
     * @param request
     * @param code
     * @param validateType
     */
    void save(ServletWebRequest request, KaptchaCode code, ValidateType validateType);

    /**
     * 获取验证码
     * @param request
     * @param validateType
     * @return
     */
    KaptchaCode get(ServletWebRequest request, ValidateType validateType);


    /**
     * 移除验证码
     * @param request
     * @param validateType
     */
    void remove(ServletWebRequest request, ValidateType validateType);

}
