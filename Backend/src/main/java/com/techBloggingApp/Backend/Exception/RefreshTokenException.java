package com.techBloggingApp.Backend.Exception;

public class RefreshTokenException extends RuntimeException {
    public RefreshTokenException(String message) {
        super(message);
    }
}
