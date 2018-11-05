package com.hlc.security.core.support.kaptcha;

import com.hlc.security.core.support.kaptcha.image.ImageKaptchaGenerateHandler;
import com.hlc.security.core.support.kaptcha.sms.SmsKaptchaGenerateHandler;
import com.hlc.security.core.support.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Liang on 2018/7/5.
 */
@Configuration
public class CaptchaConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 如果能在spring容器中找到bean name 为graphKaptchaGenerateHandler，就不用执行下面创建方法
     * @return
     */
    @Bean(name = "imageKaptchaGenerateHandler")
    @ConditionalOnMissingBean(name = "imageKaptchaGenerateHandler")
    public ImageKaptchaGenerateHandler generateKaptcha() {
        ImageKaptchaGenerateHandler generateHandler = new ImageKaptchaGenerateHandler();
        return generateHandler;
    }

    /**
     * 如果能在spring容器中找到实现SmsKaptchaGenerateHandler接口的bean,就不用执行下面创建方法
     * @return
     */
    @Bean(name = "smsKaptchaGenerateHandler")
    @ConditionalOnMissingBean(name = "smsKaptchaGenerateHandler")
    public SmsKaptchaGenerateHandler smsCodeSender() {
        SmsKaptchaGenerateHandler generateHandler = new SmsKaptchaGenerateHandler();
        return generateHandler;
    }


}

