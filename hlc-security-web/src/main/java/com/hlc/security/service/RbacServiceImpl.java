package com.hlc.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FastByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Liang on 2018/11/20.
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher =new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        //获取用户信息
        Object principal = authentication.getPrincipal();
        if(principal instanceof  UserDetails) {
            UserDetails details = (UserDetails) principal;
            String username = details.getUsername();
            System.out.println("username = " + username + "正在读取数据库查询人员权限");
        }
        //读取数据库获取用户权限
        Set<String> setstr = new HashSet<String>();
        setstr.add("/user/me1");
        String url = request.getRequestURI();
        //匹配
        boolean flag = false;
        for (String str : setstr) {
            if (antPathMatcher.match(str, url)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
