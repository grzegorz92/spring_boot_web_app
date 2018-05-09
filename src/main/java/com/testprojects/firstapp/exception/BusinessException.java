package com.testprojects.firstapp.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends Exception {

    private String message;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

