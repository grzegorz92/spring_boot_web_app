package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.exception.BusinessException;
import com.testprojects.firstapp.exception.ErrorMessage;
import javassist.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@ControllerAdvice
@EnableWebMvc//with spring.mvc.throw-exception-if-no-handler-found=true in properties enables throwing personalized exception handler for 404
public class ControllerExceptionHandler {


    //FOR REST CONTROLLERS
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(BusinessException e) {

        ErrorMessage error = new ErrorMessage(e.getStatus(), e.getStatusCode(), e.getMessage());

        return new ResponseEntity(error, e.getStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {

        ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST, 400, e.getLocalizedMessage());

        return new ResponseEntity(error, error.getStatus());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleMethodNotAllowedException(HttpRequestMethodNotSupportedException e) {

        StringBuilder info = new StringBuilder();
        info.append(e.getMethod());
        info.append(" method is not supported for this request. Please use: ");
        e.getSupportedHttpMethods().forEach(t -> info.append(t + " "));

        ErrorMessage error = new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED, 405, e.getLocalizedMessage(), info.toString());

        return new ResponseEntity(error, error.getStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity handleNotFoundException(NoHandlerFoundException e) {

        ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND, 404, e.getLocalizedMessage());

        return new ResponseEntity(error, error.getStatus());
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