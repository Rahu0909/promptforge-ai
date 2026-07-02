package com.rahulagarwal.promptforge.common.exception;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException exception, HttpServletRequest request) {
        log.error("ApException exception while processing {} {}",
                request.getMethod(),
                request.getRequestURI(),
                exception);
        ErrorResponse response = new ErrorResponse(false, exception.getMessage(), exception.getErrorCode(), exception.getStatus().value(), exception.getStatus().getReasonPhrase(), List.of(), LocalDateTime.now(), request.getRequestURI());
        return ResponseEntity.status(exception.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        log.error("MethodArgumentNotValid exception while processing {} {}",
                request.getMethod(),
                request.getRequestURI(),
                exception);
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream().map(this::mapFieldError).toList();
        ErrorResponse response = new ErrorResponse(false, "Validation failed.", ErrorCode.VALIDATION_FAILED, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), validationErrors, LocalDateTime.now(), request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException exception, HttpServletRequest request) {
        log.error("Validation exception while processing {} {}",
                request.getMethod(),
                request.getRequestURI(),
                exception);
        List<ValidationError> validationErrors = exception.getConstraintViolations().stream().map(violation -> new ValidationError(violation.getPropertyPath().toString(), violation.getMessage())).toList();
        ErrorResponse response = new ErrorResponse(false, "Validation failed.", ErrorCode.VALIDATION_FAILED, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), validationErrors, LocalDateTime.now(), request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception, HttpServletRequest request) {
        log.error("Unhandled exception while processing {} {}",
                request.getMethod(),
                request.getRequestURI(),
                exception);
        ErrorResponse response = new ErrorResponse(false, "An unexpected error occurred.", ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), List.of(), LocalDateTime.now(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private ValidationError mapFieldError(FieldError fieldError) {
        return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }
}