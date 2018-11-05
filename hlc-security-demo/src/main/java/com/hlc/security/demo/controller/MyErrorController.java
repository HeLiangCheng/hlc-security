package com.hlc.security.demo.controller;

import com.hlc.security.demo.entity.User;
import com.hlc.security.demo.exception.MyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Liang on 2018/8/28.
 */
@RestController
@RequestMapping("/er")
public class MyErrorController {

    @RequestMapping("/info")
    public String info(String name) throws  Exception {
        System.out.println("info");
        return "info :" + name;
    }

    @RequestMapping("/exinfo")
    public String getInfo(String name) throws  Exception {
        System.out.println("info");
        throw new Exception("错误校验");
        //return "错误校验";
    }

    @RequestMapping("/myinfo")
    public String getMyInfo(String name) throws  Exception {
        System.out.println("myinfo");
        throw new MyException("错误校验","100");
        //return "错误校验";
    }

    @PostMapping("/user")
    public User saveUser(@Valid @RequestBody User user) {
        System.out.println(user.toString());
        user.setId(1);
        return user;
    }

}

