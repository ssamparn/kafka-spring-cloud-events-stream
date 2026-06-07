package com.explore.springcloudeventsstreamplayground.sec18.exceptions;

public class ServiceUnavailableException extends RuntimeException {

    private static final String MESSAGE = "Service unavailable";

    public ServiceUnavailableException() {
        super(MESSAGE);
    }
}
