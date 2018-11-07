package com.hlc.security.app.repository;

import com.hlc.security.core.constant.ValidateType;
import com.hlc.security.core.support.kaptcha.KaptchaCode;
import com.hlc.security.core.support.kaptcha.KaptchaException;
import com.hlc.security.core.support.kaptcha.KaptchaRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * Created by Liang on 2018/11/7.
 * APP的中不存在Session 所以将验证码存储在redis中
 */
@Component
public class RedisKaptchaRepository implements KaptchaRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void save(ServletWebRequest request, KaptchaCode code, ValidateType validateType) {
        String key = getRepositoryKey(request, validateType);
        redisTemplate.opsForValue().set(key, code, 30, TimeUnit.MINUTES);
    }

    @Override
    public KaptchaCode get(ServletWebRequest request, ValidateType validateType) {
        String key = getRepositoryKey(request, validateType);
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return (KaptchaCode) value;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateType validateType) {
        String key = getRepositoryKey(request, validateType);
        redisTemplate.delete(key);
    }


    /**
     * 构建短信验证码的Key
     *
     * @param request
     * @param type
     * @return
     */
    private String getRepositoryKey(ServletWebRequest request, ValidateType type) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new KaptchaException("请求头中必须包含deviceId参数");
        }
        return SESSION_KEY_PREFIX + type.toString().toUpperCase() + deviceId;
    }

}
