package com.hlc.security.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.hlc.security.demo.dto.QueryCondition;
import com.hlc.security.demo.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liang on 2018/8/27.
 */
@RequestMapping("/user")
@RestController
@Api(tags = "用户接口")
public class UserController {

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
    public List<User> getlistByPage(QueryCondition condition, @PageableDefault(page = 0,size = 10) Pageable page) {
        System.out.println(condition.getAgefrom()+" : "+condition.getAgeto());
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
        user.setName("update");
        user.setPassword("update");
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name ="id" ) String id) {
        System.out.println("删除的数据Id : " + id);
    }


}
