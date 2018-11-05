package com.hlc.security.core.support.properties.kaptcha;

import com.hlc.security.core.support.kaptcha.KaptchaCode;

/**
 * Created by Liang on 2018/10/26.
 */
public class GraphKaptchaProperties extends KaptchaProperties {
    private String width = "100";
    private String heigth = "40";

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeigth() {
        return heigth;
    }

    public void setHeigth(String heigth) {
        this.heigth = heigth;
    }

}
