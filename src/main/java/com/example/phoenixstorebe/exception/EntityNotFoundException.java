package com.example.phoenixstorebe.exception;

import org.apache.logging.log4j.message.StringFormattedMessage;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String object) {
        super(String.format("%s not found",object));
    }
}
