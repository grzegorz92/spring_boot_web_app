package com.testprojects.firstapp.exception;


import org.springframework.http.HttpStatus;

public class BusinessException extends Exception{

    private String message;
    private HttpStatus status;

    public BusinessException(String message) {
        super(message);
        this.message=message;
    }

    public BusinessException(String message, HttpStatus status) {

        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
