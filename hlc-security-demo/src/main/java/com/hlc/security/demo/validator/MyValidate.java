package com.hlc.security.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Liang on 2018/8/28.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyValidateMethod.class )
public @interface MyValidate {

    String message() default "{调用服务验证失败}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
