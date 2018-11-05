package com.hlc.security.core.support.kaptcha.sms;

import com.hlc.security.core.support.kaptcha.AbstractKaptchaCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by Liang on 2018/10/27.
 */
@Component("smsKaptchaCodeProcessor")
public class SmsKaptchaCodeProcessor extends AbstractKaptchaCodeProcessor<SmsKaptchaCode> {

    @Autowired
    private SmsSender smsSender;

    @Override
    public void send(ServletWebRequest request, SmsKaptchaCode code) {
        try {
            String mobliestr = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
            smsSender.sendSmsCode(code.getCode(), mobliestr);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
