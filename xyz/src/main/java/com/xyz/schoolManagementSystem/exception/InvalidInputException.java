package com.xyz.schoolManagementSystem.exception;

public class InvalidInputException extends RuntimeException {

    // Constructor that accepts a message
    public InvalidInputException(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
