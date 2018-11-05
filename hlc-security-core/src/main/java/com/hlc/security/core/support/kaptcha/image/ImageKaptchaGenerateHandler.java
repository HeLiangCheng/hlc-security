package com.hlc.security.core.support.kaptcha.image;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.hlc.security.core.support.kaptcha.KaptchaCode;
import com.hlc.security.core.support.kaptcha.KaptchaGenerateHandler;
import com.hlc.security.core.support.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * Created by Liang on 2018/10/26.
 * 验证码生成器
 */
@Component("imageKaptchaGenerateHandler")
public class ImageKaptchaGenerateHandler implements KaptchaGenerateHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public KaptchaCode generateKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", securityProperties.getVerifycode().getImage().getWidth().toString());
        properties.setProperty("kaptcha.image.height", securityProperties.getVerifycode().getImage().getHeigth().toString());
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        properties.setProperty("kaptcha.textproducer.char.length", securityProperties.getVerifycode().getImage().getLength().toString());
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        String text = defaultKaptcha.createText();
        BufferedImage bimage = defaultKaptcha.createImage(text);
        KaptchaCode code = new ImageKaptchaCode(text, bimage, 30);
        return code;
    }

}
