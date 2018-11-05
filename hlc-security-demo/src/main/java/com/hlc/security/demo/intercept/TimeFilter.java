package com.hlc.security.demo.intercept;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Liang on 2018/8/28.
 *  过滤器
 *  可以获取到原始的HTTP请求信息，不能获取所调用的方法和控制器信息
 */
public class TimeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化拦截器");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long nowdate = new Date().getTime();
        System.out.println("doFilter 拦截器开始处理");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("doFilter 拦截器处理完毕");
        long enddate = new Date().getTime();
        System.out.println(" 请求耗时：" + (enddate-nowdate));
    }

    @Override
    public void destroy() {
        System.out.println("销毁拦截器");
    }
}
