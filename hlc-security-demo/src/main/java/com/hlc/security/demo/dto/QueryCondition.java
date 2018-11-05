package com.hlc.security.demo.dto;

import java.io.Serializable;

/**
 * Created by Liang on 2018/8/27.
 */
public class QueryCondition implements Serializable{

    private String name;
    private int agefrom;
    private int ageto;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgefrom() {
        return agefrom;
    }

    public void setAgefrom(int agefrom) {
        this.agefrom = agefrom;
    }

    public int getAgeto() {
        return ageto;
    }

    public void setAgeto(int ageto) {
        this.ageto = ageto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
