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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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