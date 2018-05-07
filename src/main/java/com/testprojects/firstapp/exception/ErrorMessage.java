package com.testprojects.firstapp.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {

    @JsonProperty("Status")
    private HttpStatus status;

    @JsonProperty("Status Code")
    private Integer statusCode;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("More Info")
    private String moreInfo;


    public ErrorMessage(HttpStatus status, Integer statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorMessage(HttpStatus status, Integer statusCode, String message, String moreInfo) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.moreInfo = moreInfo;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getMoreInfo() {
        return moreInfo;
    }
}
