package com.hlc.security.demo.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Liang on 2018/8/28.
 *  swagger 配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hlc.security.demo.controller"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) //扫描有@ApiOperation的接口
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("hlc-security-demo")
                .description("认证授权 RESTful APIs")
                .termsOfServiceUrl("")
                .contact(new Contact("heliangcheng", "", "hlc19890519@126.com"))
                .version("1.0")
                .build();
    }
}
