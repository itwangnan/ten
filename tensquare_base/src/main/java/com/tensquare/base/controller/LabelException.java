package com.tensquare.base.controller;

import com.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LabelException {

    //标注什么捕获什么异常
    @ExceptionHandler(value = Exception.class)
    public Result err(Exception e){
        e.printStackTrace();
        return new Result(false,20001,e.getMessage());
    }
}
