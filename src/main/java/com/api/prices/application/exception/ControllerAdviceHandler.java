package com.api.prices.application.exception;

import com.api.prices.infrastructure.utils.ResponseBuilder;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ControllerAdviceHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<?> handlePriceNotFound(PriceNotFoundException ex) {
        log.warn("Precio no encontrado: {}", ex.getMessage());
        return ResponseBuilder.with(HttpStatus.NOT_FOUND, false, null, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Argumento ilegal: {}", ex.getMessage());
        return ResponseBuilder.with(HttpStatus.BAD_REQUEST, false, null, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("El parámetro '%s' debe ser del tipo '%s'.", ex.getName(), ex.getRequiredType().getSimpleName());
        log.warn("Type mismatch: {}", message);
        return ResponseBuilder.with(HttpStatus.BAD_REQUEST, false, null, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage()));
        log.warn("Errores de validación: {}", errors);
        return ResponseBuilder.with(HttpStatus.BAD_REQUEST, false, errors, "Errores de validación en los campos.");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("Violaciones de constraint: {}", ex.getMessage());
        return ResponseBuilder.with(HttpStatus.BAD_REQUEST, false, null, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpected(Exception ex) {
        log.error("Error inesperado", ex); // Stacktrace completo en log
        return ResponseBuilder.with(HttpStatus.INTERNAL_SERVER_ERROR, false, null, "Ha ocurrido un error inesperado.");
    }


}
