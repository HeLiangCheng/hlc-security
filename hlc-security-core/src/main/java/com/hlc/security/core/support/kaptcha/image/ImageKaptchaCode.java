package com.hlc.security.core.support.kaptcha.image;

import com.hlc.security.core.support.kaptcha.KaptchaCode;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Created by Liang on 2018/10/26.
 * 图形验证码
 */
public class ImageKaptchaCode extends KaptchaCode{

    private BufferedImage bufferedImage;

    public ImageKaptchaCode(String code, BufferedImage bufferedImage) {
        super(code,30);
        this.bufferedImage=bufferedImage;
    }

    public ImageKaptchaCode(String code, BufferedImage bufferedImage, int expireTime) {
        super(code, expireTime);
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
