package com.hlc.security.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.hlc.security.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Liang on 2018/8/27.
 */
@RequestMapping("/user")
@RestController
@Api(tags = "用户接口")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;


    //@RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "获取人员列表信息", notes = "获取人员列表信息")
    public List<User> getlist(@RequestParam(name ="name", required = false,defaultValue ="default" ) String name) {
        System.out.println("+++++++"+name+"++++++++++");
        List<User> userList = new ArrayList<User>();
        userList.add(new User("jim", "123456", "jim@126.com"));
        userList.add(new User("tom", "123456", "tom@126.com"));
        userList.add(new User("lucy", "123456", "lucy@126.com"));
        return userList;
    }

    @GetMapping("/list")
    public List<User> getlistByPage(User condition, @PageableDefault(page = 0,size = 10) Pageable page) {
        List<User> userList = new ArrayList<User>();
        userList.add(new User("jim", "123456", "jim@126.com"));
        userList.add(new User("tom", "123456", "tom@126.com"));
        userList.add(new User("lucy", "123456", "lucy@126.com"));
        return userList;
    }

    // id只能传输数字，使用正则表达式限定
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    @JsonView(User.UserDetailView.class)
    public User getdetail(@PathVariable(name = "id") String id) {
        System.out.println("id:  " + id);
        User user = new User("test", "123456", "test@126.com");
        return user;
    }

    @PostMapping
    public User saveUser(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()){
            for(ObjectError error : result.getAllErrors()){
                System.out.println(error.getDefaultMessage());
            }
        }
        System.out.println(user.toString());
        user.setId(1);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user, BindingResult result) {
        System.out.println("修改人员id=" + user.getId());
        System.out.println(user.toString());
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(error -> {
                FieldError field =(FieldError)error;
                System.out.println(field.getField()+" : "+field.getDefaultMessage());
            });
        }
        user.setUsername("update");
        user.setPassword("update");
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name ="id" ) String id) {
        System.out.println("删除的数据Id : " + id);
    }

    //获取 Spring Security中用户信息 方式1
    @GetMapping("/me1")
    public Authentication getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    //获取 Spring Security中用户信息 方式2
    @GetMapping("/me2")
    public Authentication getUserInfo2(Authentication authentication) {
        return authentication;
    }

    //获取 Spring Security中用户信息 方式3  Principal
    @GetMapping("/me3")
    public Object getUserInfo3(@AuthenticationPrincipal UserDetails user) {
        return user;
    }


    @GetMapping("/resetPwd")
    public String resetPwd(){
        return "修改密码";
    }

    //用户注册
    @PostMapping("/register")
    public void register(User user,HttpServletRequest request) {
        // 无论是注册还是绑定，都会拿到一个唯一标识
        String userId = user.getUsername();
        // 设置两种浏览器和App社交登录后注册的不同的处理类型
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
    }

}
