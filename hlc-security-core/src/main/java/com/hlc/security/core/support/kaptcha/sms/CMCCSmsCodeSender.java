package com.hlc.security.core.support.kaptcha.sms;

import org.springframework.stereotype.Component;

/**
 * Created by Liang on 2018/10/26.
 */
@Component("smsSender")
public class CMCCSmsCodeSender implements SmsSender {

    @Override
    public void sendSmsCode(String smscode, String mobile) {
        System.out.println("------欢迎使用中国移动短信运营商---------");
        System.out.println("正在向手机：" + mobile + "发送验证码：" + smscode);
    }

}
