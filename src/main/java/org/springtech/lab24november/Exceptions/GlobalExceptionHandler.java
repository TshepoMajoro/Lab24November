package org.springtech.lab24november.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> MapToResponseEntity(HttpStatus status,
                                                                    String message)
    {
        Map<String, Object> responseBody = new LinkedHashMap<>();

        responseBody.put("Code",status.value());
        responseBody.put("Error",status.getReasonPhrase());
        responseBody.put("Message",message);

        return ResponseEntity.status(status).body(responseBody);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ResourceNotFoundException exception
    )
    {
        return MapToResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicates(
            DuplicateResourceException exception
    )
    {
        return MapToResponseEntity(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException exception
    )
    {
        Map<String, Object> validationErrors = new LinkedHashMap<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.stream().map(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));

        Map<String,Object> body = new LinkedHashMap<>();
        body.put("Code",HttpStatus.BAD_REQUEST.value());
        body.put("Error",HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("messages",validationErrors);

        return ResponseEntity.badRequest().body(body);
    }
}
