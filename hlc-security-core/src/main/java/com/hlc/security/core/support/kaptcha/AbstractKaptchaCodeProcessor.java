package com.hlc.security.core.support.kaptcha;

import com.hlc.security.core.constant.ValidateType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;
import java.util.Map;


/**
 * Created by Liang on 2018/10/27.
 * 验证码处理抽象类
 * 封装基础处理流程
 * 使用模板方法模式：将统一的业务流程逻辑进行封装
 */
public abstract  class AbstractKaptchaCodeProcessor<T extends KaptchaCode> implements KaptchaCodeProcessor {

    /**
     * spring容器依赖搜索，搜索所有实现KaptchaGenerateHandler接口的bean,将bean注入到map集合中，并以bean name
     * 作为key，bean对象作为value保存
     */
    @Autowired
    private Map<String, KaptchaGenerateHandler> generateHandlerMap;
    private SessionStrategy strategy = new HttpSessionSessionStrategy();
    @Autowired
    private KaptchaRepository kaptchaRepository;


    @Override
    public void processor(ServletWebRequest request) {
        //验证码生成
        T code = generate(request);
        //验证码存储
        save(request, code);
        //验证码发送
        send(request, code);
    }

    /**
     * 验证码生成
     *
     * @return
     */
    private T generate(ServletWebRequest request) throws KaptchaException {
        //根据请求确定请求验证码类型： /security/kaptcha/image   /security/kaptcha/sms
        String codetype = StringUtils.substringAfter(request.getRequest().getRequestURI(), "/kaptcha/");
        String beankey = codetype + "KaptchaGenerateHandler";
        if (generateHandlerMap.containsKey(beankey)) {
            KaptchaGenerateHandler handler = generateHandlerMap.get(beankey);
            KaptchaCode code = handler.generateKaptcha();
            return (T) code;
        } else {
            throw new KaptchaException("未找到验证码生成所依赖的bean");
        }
    }

    /**
     * 验证码存储
     *
     * @param request
     */
    private void save(ServletWebRequest request, T code) {
        KaptchaCode kaptcode = new KaptchaCode(code.getCode(), code.getExpireTime());
        kaptchaRepository.save(request, kaptcode, getValidateType());
    }

    /**
     * 验证码发送
     *
     * @param code
     */
    public abstract void send(ServletWebRequest request, T code);

    @Override
    public void validate(ServletWebRequest request) throws Exception {
        ValidateType codeType = getValidateType();
        KaptchaCode code = kaptchaRepository.get(request, getValidateType());
        String reqValicode = null;
        try {
            reqValicode = ServletRequestUtils.getStringParameter(request.getRequest(), codeType.getValidateTypeByParam());
        } catch (ServletRequestBindingException ex) {
            throw new KaptchaException("获取验证码的值失败");
        }
        if (code == null) {
            throw new KaptchaException("验证码在回话中不存在");
        }
        if (StringUtils.isEmpty(reqValicode)) {
            throw new KaptchaException("输入验证码不能为空");
        }
        if (LocalDateTime.now().isAfter(code.getExpireTime())) {
            kaptchaRepository.remove(request, getValidateType());
            throw new KaptchaException("验证码已经过期");
        }
        if (!reqValicode.equals(code.getCode())) {
            throw new KaptchaException("验证码不匹配");
        }
        kaptchaRepository.remove(request, getValidateType());
    }

    /**
     * 根据初始化子类获取校验类型
     *
     * @return
     */
    private ValidateType getValidateType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "KaptchaCodeProcessor");
        return ValidateType.valueOf(type.toUpperCase());
    }

}
