package com.example.textpolish_service.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", ZonedDateTime.now().format(FORMATTER));
        body.put("status", ex.getStatusCode().value());
        body.put("error", ex.getReason());
        body.put("path", "/polish");

        return new ResponseEntity<>(body, ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", ZonedDateTime.now().format(FORMATTER));
        body.put("status", ex.getStatusCode().value());
        body.put("error", ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        body.put("path", "/polish");

        return new ResponseEntity<>(body, ex.getStatusCode());
    }
}
