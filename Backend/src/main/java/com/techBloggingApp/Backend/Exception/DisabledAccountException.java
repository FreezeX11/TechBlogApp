package com.techBloggingApp.Backend.Exception;

public class DisabledAccountException extends RuntimeException {
    public DisabledAccountException(String message) {
        super(message);
    }
}
