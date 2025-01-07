package com.school.exception;

public class ClassAlreadyExistsException extends RuntimeException {
    public ClassAlreadyExistsException(String message) {
        super(message);
    }
}
