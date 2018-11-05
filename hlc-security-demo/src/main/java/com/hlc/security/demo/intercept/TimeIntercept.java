package com.hlc.security.demo.intercept;


import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Liang on 2018/8/28.
 * 拦截器
 *  可以获取请求的Controller和处理方法， 但是不能获取调用方法传递的值，
 */
@Component
public class TimeIntercept implements HandlerInterceptor {

    /* 拦截器开始执行 */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        System.out.println("TimeIntercept ---  preHandle");
        HandlerMethod hmd = (HandlerMethod) handler;
        System.out.println(hmd.getBean().getClass().getName());
        System.out.println(hmd.getMethod().getName());
        httpServletRequest.setAttribute("starttime", new Date().getTime());
        return true;
    }

    /* 拦截器开始执行 */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("TimeIntercept ---  postHandle");
        long starttime = Long.parseLong(httpServletRequest.getAttribute("starttime").toString());
        long haoshi = new Date().getTime() - starttime;
        System.out.println("postHandle拦截器耗时： " + haoshi);
    }

    /* 拦截器开始执行 */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        System.out.println("TimeIntercept ---  afterCompletion");
        long starttime = Long.parseLong(httpServletRequest.getAttribute("starttime").toString());
        long haoshi = new Date().getTime() - starttime;
        System.out.println("afterCompletion拦截器耗时： " + haoshi);
        System.out.println(e);
    }

}
