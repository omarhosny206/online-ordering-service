package com.example.util;

import org.springframework.http.HttpStatus;

public class ApiError {
    public static CustomException badRequest(String message) {
        return new CustomException(message, HttpStatus.BAD_REQUEST);
    }

    public static CustomException unauthorized(String message) {
        return new CustomException(message, HttpStatus.UNAUTHORIZED);
    }

    public static CustomException forbidden(String message) {
        return new CustomException(message, HttpStatus.FORBIDDEN);
    }


    public static CustomException notFound(String message) {
        return new CustomException(message, HttpStatus.NOT_FOUND);
    }

    public static CustomException conflict(String message) {
        return new CustomException(message, HttpStatus.CONFLICT);
    }

    public static CustomException internalServerError(String message) {
        return new CustomException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static CustomException of(String message, HttpStatus httpStatus) {
        return new CustomException(message, httpStatus);
    }

    public static CustomException from(Exception exception) {
        if (exception instanceof CustomException) {
            return (CustomException) exception;
        }

        return internalServerError(exception.getMessage());
    }
}
