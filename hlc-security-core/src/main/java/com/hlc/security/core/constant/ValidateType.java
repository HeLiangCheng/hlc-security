package com.hlc.security.core.constant;

/**
 * Created by Liang on 2018/10/26.
 * 校验数据类型
 */
public enum ValidateType {

    IMAGE {
        @Override
        public String getValidateTypeByParam() {
            return SecurityConstant.DEFAULT_VALIDATETYPE_IMAGE;
        }
    },
    SMS {
        @Override
        public String getValidateTypeByParam() {
            return SecurityConstant.DEFAULT_VALIDATETYPE_SMS;
        }
    };

    /**
     * 根据请求获取验证类型
     * @return
     */
    public abstract String getValidateTypeByParam();

}
