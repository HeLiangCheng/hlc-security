package com.hlc.security.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liang on 2018/8/27.
 */
public class User implements Serializable{

    public interface  UserSimpleView{}
    public interface  UserDetailView extends UserSimpleView{}

    private int id;
    private String username;
    @NotNull(message ="密码不能为空" )
    private String password;
    private String email;
    //past 注解表示必须是一个过去时间
    @Past(message = "出生日期不能为未来时间")
    private Date birthdate;


    public User(){}
    public User(String name, String password, String email) {
        this.setUsername(name);
        this.setPassword(password);
        this.setEmail(email);
    }

    @JsonView(UserSimpleView.class)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthdate='" + birthdate + '\'' +
                '}';

    }
}
