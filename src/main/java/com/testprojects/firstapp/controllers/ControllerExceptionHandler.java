package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice(basePackageClasses = PropertiesController.class)
public class ControllerExceptionHandler {



    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleException(BusinessException e) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ER");
        return modelAndView;
    }

}


