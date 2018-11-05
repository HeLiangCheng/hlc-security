package com.hlc.security.demo.exception;

/**
 * Created by Liang on 2018/8/28.
 */
public class MyException  extends Exception {

    private String id;

    public MyException() {
    }

    public MyException(String msg,String id) {
        super(msg);
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
