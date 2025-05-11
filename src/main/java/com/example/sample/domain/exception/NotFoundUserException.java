package com.example.sample.domain.exception;

public class NotFoundUserException extends Exception {
    private static final long serialVersionUID = 1L;

    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
