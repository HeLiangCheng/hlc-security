package com.hlc.security.demo.intercept;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Liang on 2018/8/28.
 *  可以获取请求参数，但是获取不到原始的HTTP请求信息 HttpServletRequest  HttpServletResponse
 */
@Aspect
@Component
public class TimeAspect {

    @Around("execution(* com.hlc.security.demo.controller.MyErrorController.*(..))")
    public Object handlerTimeAspect(ProceedingJoinPoint point) throws Throwable {
        System.out.println("切面已经调用");
        long nowdate = new Date().getTime();
        Object[] para = point.getArgs();
        for (Object item : para) {
            System.out.println("参数 :"+item);
        }
        point.proceed(); //请求放行
        long haoshi = new Date().getTime() - nowdate;
        System.out.println("TimeAspect 请求耗时：" + haoshi);
        return null;
    }

}
