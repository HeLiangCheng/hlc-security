package com.hlc.security.core.support.kaptcha;

import com.hlc.security.core.support.kaptcha.image.ImageKaptchaCode;
import com.hlc.security.core.support.kaptcha.image.ImageKaptchaGenerateHandler;
import com.hlc.security.core.support.kaptcha.sms.SmsKaptchaCode;
import com.hlc.security.core.support.kaptcha.sms.SmsKaptchaGenerateHandler;
import com.hlc.security.core.support.kaptcha.sms.SmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Liang on 2018/10/24.
 */
@RequestMapping("/security")
@RestController
public class KaptchaController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private Map<String,KaptchaCodeProcessor> kaptchaCodeProcessorMap;


    @GetMapping("/kaptcha/{type}")
    public void kaptcha(HttpServletRequest request,
                              HttpServletResponse response,
                              @PathVariable(value ="type") String codetype) throws Exception {
        logger.info("请求验证码类型：" + codetype);
        String proKey = codetype + "KaptchaCodeProcessor";
        if (kaptchaCodeProcessorMap != null && kaptchaCodeProcessorMap.containsKey(proKey)) {
            KaptchaCodeProcessor kaptchaProcessor = kaptchaCodeProcessorMap.get(proKey);
            ServletWebRequest webRequest = new ServletWebRequest(request, response);
            kaptchaProcessor.processor(webRequest);
        }
    }


}
