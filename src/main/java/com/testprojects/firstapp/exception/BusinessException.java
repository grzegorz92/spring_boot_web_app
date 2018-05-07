package com.testprojects.firstapp.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends Exception{

    private String message;
    private HttpStatus status;
    private Integer statusCode;

    public BusinessException(String message) {
        super(message);
        this.message=message;
    }

    public BusinessException(String message, HttpStatus status, Integer statusCode) {

        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
