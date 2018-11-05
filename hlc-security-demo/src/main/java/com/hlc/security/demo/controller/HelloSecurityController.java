package com.hlc.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Liang on 2018/8/27.
 */
@RequestMapping("/hello")
@RestController
public class HelloSecurityController {

    @GetMapping("/test")
    public String hello(){
        System.out.println("hello Security");
        return "hello Security";
    }

}
