package com.hlc.security.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Liang on 2018/11/1.
 */
@RequestMapping
@RestController
@Api(tags = "用户接口")
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "hello hlc-security-web ";
    }

}
