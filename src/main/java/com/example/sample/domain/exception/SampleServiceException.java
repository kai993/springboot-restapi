package com.example.sample.domain.exception;

public class SampleServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SampleServiceException(String message) {
        super(message);
    }

    public SampleServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

