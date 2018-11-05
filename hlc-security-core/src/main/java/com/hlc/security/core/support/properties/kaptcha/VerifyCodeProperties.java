package com.hlc.security.core.support.properties.kaptcha;

import com.hlc.security.core.support.properties.kaptcha.GraphKaptchaProperties;
import com.hlc.security.core.support.properties.kaptcha.KaptchaProperties;

/**
 * Created by Liang on 2018/10/26.
 */
public class VerifyCodeProperties {

    private KaptchaProperties sms = new KaptchaProperties();

    private GraphKaptchaProperties image = new GraphKaptchaProperties();

    public KaptchaProperties getSms() {
        return sms;
    }

    public void setSms(KaptchaProperties sms) {
        this.sms = sms;
    }

    public GraphKaptchaProperties getImage() {
        return image;
    }

    public void setImage(GraphKaptchaProperties image) {
        this.image = image;
    }
}
