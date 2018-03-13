package com.xjf.controller.ControllerAdvice;


import com.xjf.exception.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TestControllerAdvice {

    @ExceptionHandler(value = MyException.class)
    public String toErrorPage (MyException e) {
        System.out.println(e.getMessage());
        return "error/error";
    }

}
