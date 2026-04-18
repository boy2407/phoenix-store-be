package com.example.phoenixstorebe.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }


}
