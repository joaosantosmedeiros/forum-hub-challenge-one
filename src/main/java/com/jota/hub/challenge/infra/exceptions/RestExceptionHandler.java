package com.jota.hub.challenge.infra.exceptions;

import com.jota.hub.challenge.infra.dto.StandardResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

//TODO padronizar resposta dos controllers e exception handler
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<StandardResponseDTO<Object>> notFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardResponseDTO<>(
                ex.getMessage(),
                false,
                null
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardResponseDTO<Object>> badRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardResponseDTO<>(
                ex.getMessage(),
                false,
                null
        ));
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<StandardResponseDTO<Object>> unauthorized(SecurityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardResponseDTO<>(
                ex.getMessage(),
                false,
                null
        ));
    }

//TODO consertar
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardResponseDTO<Object>> invalidArgument(MethodArgumentNotValidException ex){
        var fields = ex.getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardResponseDTO<>(
                "",
                false,
                fields.stream().map(ErrorValidationData::new)
        ));
    }

    private record ErrorValidationData(String field, String message) {
        public ErrorValidationData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
