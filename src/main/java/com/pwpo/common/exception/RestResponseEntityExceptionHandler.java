package com.pwpo.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<List<ApiError>> handleValidationException(ValidationException ex) {
        List<ApiError> r = Collections.singletonList(new ApiError(ex.getFieldName(), ex.getCode(), ex.getMessage()));
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IntegrityValidationException.class})
    protected ResponseEntity<List<ApiError>> handleIntegrityValidationException(IntegrityValidationException ex) {
        return new ResponseEntity<>(ex.getErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<List<ApiError>> handleValidationException(NotFoundException ex) {
        ApiError error = new ApiError(null, ExceptionCode.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(Collections.singletonList(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<List<ApiError>> handleValidationException(ConstraintViolationException ex) {
        List<ApiError> r = new ArrayList<>();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        for (ConstraintViolation<?> cv : constraintViolations) {
            String field = "";
            for (Path.Node node : cv.getPropertyPath()) {
                field = node.getName();
            }
            r.add(new ApiError(field, ExceptionCode.FIELD_VALIDATION, cv.getMessage()));
        }

        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<ApiError> r = new ArrayList<>();

        List<FieldError> fieldErrors = ex
                .getBindingResult()
                .getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            r.add(new ApiError(fieldError.getField(), ExceptionCode.FIELD_VALIDATION, fieldError.getDefaultMessage()));
        }

        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }
}