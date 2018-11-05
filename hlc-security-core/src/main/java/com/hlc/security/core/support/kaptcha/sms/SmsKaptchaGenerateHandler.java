package com.hlc.security.core.support.kaptcha.sms;

import com.hlc.security.core.support.kaptcha.KaptchaCode;
import com.hlc.security.core.support.kaptcha.KaptchaGenerateHandler;
import com.hlc.security.core.support.properties.SecurityProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Liang on 2018/10/26.
 */
@Component("smsKaptchaGenerateHandler")
public class SmsKaptchaGenerateHandler implements KaptchaGenerateHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public KaptchaCode generateKaptcha() {
        String code = RandomStringUtils.randomNumeric(6);
        SmsKaptchaCode smscode = new SmsKaptchaCode();
        smscode.setCode(code);
        return smscode;
    }


}
