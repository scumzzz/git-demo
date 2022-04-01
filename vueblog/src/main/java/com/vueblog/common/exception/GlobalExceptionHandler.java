package com.vueblog.common.exception;


import com.vueblog.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 日志输出
 * 全局捕获异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.UNAUTHORIZED) //因为前后端分离 返回一个状态 一般是401 没有权限
    @ExceptionHandler(value =  ShiroException.class)//捕获运行时异常ShiroException是大部分异常的父类
    public Result handler(ShiroException e){
        log.error("运行时异常：-----------------{}",e);
        return Result.fail(401,e.getMessage(),null);
    }

    /**
     * 实体校验异常
     * MethodArgumentNotValidException捕获实体校验异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) //因为前后端分离 返回一个状态
    @ExceptionHandler(value = MethodArgumentNotValidException.class)//捕获运行时异常
    public Result handler(MethodArgumentNotValidException e){
        log.error("实体校验时异常：-----------------{}",e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError =  bindingResult.getAllErrors().stream().findFirst().get();
        return Result.fail(objectError.getDefaultMessage());
    }

    /**
     * 断言异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e){
        log.error("Assert异常:------------------>{}",e);
        return Result.fail(e.getMessage());
    }




    @ResponseStatus(HttpStatus.BAD_REQUEST) //因为前后端分离 返回一个状态
    @ExceptionHandler(value =  RuntimeException.class)//捕获运行时异常
    public Result handler(RuntimeException e){
        log.error("运行时异常：-----------------{}",e);
        return Result.fail(e.getMessage());
    }
}
