package com.blaqboxdev.unsplash.Exceptions;

public class DuplicateUserEmailAlreadyExists extends RuntimeException {
    public DuplicateUserEmailAlreadyExists(String message) {
        super(message);
    }
}
