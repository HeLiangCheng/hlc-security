package com.hlc.security.core.authentication.social;

import com.hlc.security.core.authentication.mobile.SmsCodeAuthenticationToken;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Liang on 2018/11/9.
 */
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    private SocialUserDetailsService userDetailsService;
    private UsersConnectionRepository usersConnectionRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OpenIdAuthenticationToken token = (OpenIdAuthenticationToken) authentication;
        if (token != null) {
            Set<String> providerUserIds = new HashSet<>();
            String openid = token.getPrincipal().toString();
            providerUserIds.add(openid);

            Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(token.getProviderId(), providerUserIds);
            if (CollectionUtils.isEmpty(userIds) || userIds.size() != 1) {
                throw new InternalAuthenticationServiceException("无法获取用户信息");
            }
            String userId = userIds.iterator().next();
            UserDetails user = userDetailsService.loadUserByUserId(userId);
            if (user != null) {
                OpenIdAuthenticationToken authenticationResult = new OpenIdAuthenticationToken(user, user.getAuthorities());
                authenticationResult.setDetails(user);
                return authenticationResult;
            } else {
                throw new InternalAuthenticationServiceException("无法获取用户信息");
            }
        } else {
            throw new InternalAuthenticationServiceException("传入的用户信息有误");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public SocialUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(SocialUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UsersConnectionRepository getUsersConnectionRepository() {
        return usersConnectionRepository;
    }

    public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
    }


}
