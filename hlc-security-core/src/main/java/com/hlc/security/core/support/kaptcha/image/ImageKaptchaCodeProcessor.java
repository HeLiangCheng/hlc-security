package com.hlc.security.core.support.kaptcha.image;

import com.hlc.security.core.support.kaptcha.AbstractKaptchaCodeProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Liang on 2018/10/27.
 */
@Component("imageKaptchaCodeProcessor")
public class ImageKaptchaCodeProcessor extends AbstractKaptchaCodeProcessor<ImageKaptchaCode> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void send(ServletWebRequest request, ImageKaptchaCode code) {

        //输入图片流简写
        // ImageIO.write(code.getBufferedImage(),"jpg",request.getResponse().getOutputStream());

        //原始发送图片验证码
        HttpServletResponse response = request.getResponse();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(code.getBufferedImage(), "jpg", out);
            out.flush();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    logger.error("关闭流失败：" + ex.getMessage());
                }
            }
        }
    }

}
