package com.testprojects.firstapp.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView NullPointerExHandler(){

        logger.error("NullPointerException Caught");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ER");

        return modelAndView;
    }


}
