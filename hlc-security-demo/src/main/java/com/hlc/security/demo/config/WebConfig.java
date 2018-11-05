package com.hlc.security.demo.config;

import com.hlc.security.demo.intercept.TimeFilter;
import com.hlc.security.demo.intercept.TimeIntercept;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Liang on 2018/8/28.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new TimeIntercept());
    }

    @Bean
    public FilterRegistrationBean  registFilter() {
        FilterRegistrationBean registrate = new FilterRegistrationBean();
        TimeFilter timeFilter = new TimeFilter();
        registrate.setFilter(timeFilter);
        ArrayList url = new ArrayList();
        url.add("/*");
        registrate.setUrlPatterns(url);
        return registrate;
    }

}
