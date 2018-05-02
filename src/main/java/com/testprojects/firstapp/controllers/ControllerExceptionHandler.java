package com.testprojects.firstapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class ControllerExceptionHandler {



    //FOR REST CONTROLLERS

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e){

        String message = e.getMessage();

        return message;
    }

    //FOR "NORMAL" CONTROLLERS

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleException(){
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("ER");
//        return modelAndView;
//    }
}
