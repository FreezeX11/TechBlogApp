package com.techBloggingApp.Backend.Exception;

import com.techBloggingApp.Backend.Payload.Response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        return new ApiErrorResponse(
                "Resource not found",
                404,
                e.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );
    }

    @ExceptionHandler(RefreshTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleRefreshTokenException(RefreshTokenException e, HttpServletRequest request) {
        return new ApiErrorResponse(
                "FORBIDDEN",
                403,
                e.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBusinessRuleException(BusinessException ex, HttpServletRequest req) {
        return new ApiErrorResponse(
                "Business Rule Violation",
                400,
                ex.getMessage(),
                req.getRequestURI(),
                Instant.now()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleBadCredentials(BadCredentialsException ex, HttpServletRequest req) {
        return new ApiErrorResponse(
                "Invalid Credentials",
                401,
                ex.getMessage(),
                req.getRequestURI(),
                Instant.now()
        );
    }

    @ExceptionHandler(DisabledAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleDisableAccountException(DisabledAccountException ex, HttpServletRequest req) {
        return new ApiErrorResponse(
                "Unauthorized",
                401,
                ex.getMessage(),
                req.getRequestURI(),
                Instant.now()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .findFirst()
                .orElse("Invalid input");

        return new ApiErrorResponse(
                "Validation Error",
                400,
                message,
                req.getRequestURI(),
                Instant.now()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleGeneric(Exception ex, HttpServletRequest req) {
        return new ApiErrorResponse(
                "Internal Server Error",
                500,
                "An unexpected error occurred",
                req.getRequestURI(),
                Instant.now()
        );
    }

}
