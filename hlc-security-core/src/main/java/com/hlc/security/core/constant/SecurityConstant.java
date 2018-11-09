package com.hlc.security.core.constant;

/**
 * Created by Liang on 2018/10/26.
 * 安全配置常量
 */
public interface SecurityConstant {

    //图形验证参数名称
    public static final String DEFAULT_VALIDATETYPE_IMAGE = "image_code";

    //短信验证参数名称
    public static final String DEFAULT_VALIDATETYPE_SMS = "sms_code";

    //当请求需要身份认证时，默认跳转的url  Authentication
    public static final String DEFAULT_AUTHENTICATION_ACTION = "/authenticate/check";

    //退出登录请求
    public static final String DEFAULT_LOGOUT_ACTION = "/signOut";

    //默认用户名密码登录请求action url
    public static final String DEFAULT_USERNAMEPASSWORD_LOGIN_ACTION = "/authentication/form";

    //默认的手机验证码登录请求action url
    public static final String DEFAULT_MOBILE_LOGIN_ACTION = "/authentication/mobile";

    //一般登录页面
    public static final String DEFAULT_LOGIN_PAGE = "/login.html";

    //退出系统页面
    public static final String DEFAULT_SIGNOUT_URL = "/logout.html";

    //社交注册页面
    public static final String DEFAULT_SIGNUP_URL = "/register.html";

    //session过期配置
    public static final String DEFAULT_SESSION_INVALID = "/session/invalid";

    //默认的OPENID登录请求处理url
    String DEFAULT_PROCESSING_OPENID_LOGIN_ACTION = "/authentication/openid";

    //openid参数名
    String DEFAULT_PARAMETER_NAME_OPEN_ID = "openId";

    //providerId参数名
    String DEFAULT_PARAMETER_NAME_PROVIDER_ID = "providerId";

}
