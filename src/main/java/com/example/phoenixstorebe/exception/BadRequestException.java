package com.example.phoenixstorebe.exception;

import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BadRequestException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(BadRequestException.class);

    public BadRequestException(String message) {
        super(message);
        log.warn("BadRequestException: {}", message);
    }
}
