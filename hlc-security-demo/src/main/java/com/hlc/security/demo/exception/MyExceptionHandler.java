package com.hlc.security.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liang on 2018/8/28.
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> HandlerMyException(MyException ex) {
        Map<String, Object> resultmap = new HashMap<String, Object>();
        resultmap.put("message", ex.getMessage());
        resultmap.put("id", ex.getId());
        return resultmap;
    }

}
