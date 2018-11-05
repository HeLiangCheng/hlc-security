package com.hlc.security.core.support.kaptcha.sms;

/**
 * Created by Liang on 2018/10/26.
 * 短信发送器
 */
public interface SmsSender {
    void sendSmsCode(String smscode, String mobile);
}
