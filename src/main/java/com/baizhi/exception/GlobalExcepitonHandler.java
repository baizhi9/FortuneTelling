package com.baizhi.exception;


import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * 简单的全局异常捕获
 */
@RestControllerAdvice
public class GlobalExcepitonHandler {

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public String handle401(ShiroException e) {
        System.out.println("ShiroException");
        return "授权异常，请重新登录";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UnknownAccountException.class)
    public String handler(UnknownAccountException e) throws IOException {
        System.out.println("UnknownAccountException");
        return "用户名错误！！！";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public String handler(IncorrectCredentialsException e) throws IOException {
        System.out.println("IncorrectCredentialsException");
        return "密码错误！！！";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public String handler(RuntimeException e) throws IOException {
        System.out.println("RuntimeException");
        return "系统异常，联系管理员";
    }
}

