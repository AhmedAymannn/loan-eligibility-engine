package com.EXbank.loan_eligibility_engine.api.exception;

import com.EXbank.loan_eligibility_engine.api.dto.response.ErrorResponseDTO;
import com.EXbank.loan_eligibility_engine.api.dto.response.FieldErrorDetail;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.EXbank.loan_eligibility_engine.api.dto.response.ValidationErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<FieldErrorDetail> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorDetail(
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage()
                ))
                .toList();

        ValidationErrorResponseDTO errorPayload = new ValidationErrorResponseDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Input payload validation failed",
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorPayload);
    }

    @ExceptionHandler(ProductCodeNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(
            ProductCodeNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponseDTO body = new ErrorResponseDTO(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
