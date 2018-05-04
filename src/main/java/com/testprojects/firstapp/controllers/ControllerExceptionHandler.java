package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class ControllerExceptionHandler {


    //FOR REST CONTROLLERS

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String handleException(BusinessException e) {

        return e.getMessage() + ", CODE: " + e.getStatus();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingServletRequestParameterException(MissingServletRequestParameterException e, WebRequest request) {

        return "'" + e.getParameterName() + "' parameter is not present! Status Code: ";
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleMethodNotAllowedException( HttpRequestMethodNotSupportedException e) {


        String supportedMethods="";
        for (String iter: e.getSupportedMethods() ){
            supportedMethods = "'"+iter+"' "+supportedMethods;
        }

       return "'"+e.getMethod()+"' method is not allowed here. Please use "+supportedMethods;

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
