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
    protected ResponseEntity<List<ExceptionBadRequestResponse>> handleValidationException(ValidationException ex) {
        List<ExceptionBadRequestResponse> r = Collections.singletonList(new ExceptionBadRequestResponse(ex.getFieldName(), ex.getCode(), ex.getMessage()));
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<List<ExceptionBadRequestResponse>> handleValidationException(NotFoundException ex) {
        List<ExceptionBadRequestResponse> r = Collections.singletonList(new ExceptionBadRequestResponse(null, ExceptionCode.NOT_FOUND, ex.getMessage()));
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<List<ExceptionBadRequestResponse>> handleValidationException(ConstraintViolationException ex) {
        List<ExceptionBadRequestResponse> r = new ArrayList<>();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        for (ConstraintViolation<?> cv : constraintViolations) {
            String field = "";
            for (Path.Node node : cv.getPropertyPath()) {
                field = node.getName();
            }
            r.add(new ExceptionBadRequestResponse(field, ExceptionCode.FIELD_VALIDATION, cv.getMessage()));
        }

        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<ExceptionBadRequestResponse> r = new ArrayList<>();

        List<FieldError> fieldErrors = ex
                .getBindingResult()
                .getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            r.add(new ExceptionBadRequestResponse(fieldError.getField(), ExceptionCode.FIELD_VALIDATION, fieldError.getDefaultMessage()));
        }

        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }
}