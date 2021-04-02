package com.neueda.url.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UrlCreationException extends RuntimeException {

    private String message;
    private int errorCode;

    public UrlCreationException(String message, int errorCode) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
