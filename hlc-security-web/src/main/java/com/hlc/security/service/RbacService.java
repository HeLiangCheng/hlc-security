package com.hlc.security.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Liang on 2018/11/20.
 */
public interface RbacService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
