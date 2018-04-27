package com.testprojects.firstapp.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ControllerAdvice
public class ControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    @ExceptionHandler(Exception.class)
    public ModelAndView exHandler(String exType){

        logger.error("Exception Caught: "+exType);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ER");

        return modelAndView;
    }


}
