package com.hlc.security.demo.validator;

import com.hlc.security.demo.entity.User;
import com.hlc.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Liang on 2018/8/28.
 * spring ConstraintValidator 这个接口会直接被spring容器初始化Bean，所以不需要添加 @Component 注解
 */
public class MyValidateMethod implements ConstraintValidator<MyValidate,String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(MyValidate myValidate) {
        System.out.println("初始化验证器");
    }

    //校验逻辑
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("当前被校验值：" + s);
        User user = userService.validateUserName(s);
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

}
