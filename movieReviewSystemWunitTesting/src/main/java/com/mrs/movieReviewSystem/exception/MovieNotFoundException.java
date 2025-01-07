package com.mrs.movieReviewSystem.exception;


public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String message) {
        super(message);
    }
}

