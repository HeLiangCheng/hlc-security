package com.hlc.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Created by Liang on 2018/10/19.
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService,SocialUserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        //按用户名密码查找
        if ("admin".equals(username)) {
            logger.info("开始验证用户名密码");
            //user = new User("admin", "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
            String pwdStr = passwordEncoder.encode("123456");
            System.out.println(pwdStr);
            user = new User("admin", pwdStr, true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
            logger.info("验证成功");
        }
        //按手机号码查找
        if ("123456".equals(username)) {
            logger.info("开始验证用户手机验证码");
            user = new User("admin", "123456", true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
            logger.info("验证成功");
        }
        return user;
    }


    //社交登录时使用，传入参数是
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("社交登录User");
        String pwdStr = passwordEncoder.encode("123456");
        SocialUser user = new SocialUser("admin", pwdStr, true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return user;
    }

}
