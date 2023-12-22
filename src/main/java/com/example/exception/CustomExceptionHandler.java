package com.example.exception;

import com.example.dto.ErrorDto;
import com.example.util.CustomException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("MethodArgumentNotValidException class=" + ex.getClass() + ", status=BAD_REQUEST(400)");
        Map<String, String> result = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
                    String field = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    result.put(field, message);
                }
        );

        return ResponseEntity.badRequest().body(result);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        log.error("ConstraintViolationException class=" + ex.getClass() + ", status=BAD_REQUEST(400)");
        System.out.println(ex.getClass());
        Map<Path, String> result = new HashMap<>();

        ex.getConstraintViolations().forEach(constraintViolation -> {
            result.put(constraintViolation.getPropertyPath(), constraintViolation.getMessage());
        });

        return ResponseEntity.badRequest().body(result);
    }

    @ExceptionHandler(value = {AuthenticationException.class, JwtException.class})
    public ResponseEntity<ErrorDto> handleAuthenticationException(Exception ex, WebRequest request) {
        log.error("AuthenticationException or JwtException class=" + ex.getClass() + ", status=UNAUTHORIZED(401), message=" + ex.getMessage());
        ErrorDto errorDto = new ErrorDto(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAuthorizationException(Exception ex, WebRequest request) {
        log.error("AccessDeniedException class=" + ex.getClass() + ", status=FORBIDDEN(403), message=" + ex.getMessage());
        ErrorDto errorDto = new ErrorDto(ex.getMessage() + ", you are not authorized to access this resource", HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDto> handleCustomException(CustomException ex, WebRequest request) {
        log.error("CustomException status=" + ex.getHttpStatus().toString() + "("
                + ex.getHttpStatus().value() + "), message=" + ex.getMessage());
        ErrorDto errorDto = new ErrorDto(ex.getMessage(), ex.getHttpStatus().value());
        return new ResponseEntity<>(errorDto, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Generic Exception class=" + ex.getClass() + ", status=INTERNAL_SERVER_ERROR(500)" +
                ", message=" + ex.getMessage());
        ErrorDto errorDto = new ErrorDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
