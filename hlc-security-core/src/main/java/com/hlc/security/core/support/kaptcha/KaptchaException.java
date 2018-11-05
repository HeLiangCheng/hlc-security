package com.hlc.security.core.support.kaptcha;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by Liang on 2018/10/24.
 */
public class KaptchaException extends AuthenticationException {

    public KaptchaException(String msg, Throwable t) {
        super(msg, t);
    }

    public KaptchaException(String msg) {
        super(msg);
    }
}
