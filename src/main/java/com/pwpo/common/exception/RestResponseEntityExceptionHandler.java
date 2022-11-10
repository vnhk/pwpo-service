package com.pwpo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<ExceptionBadRequestResponse> handleValidationException(ValidationException ex) {
        ExceptionBadRequestResponse r = new ExceptionBadRequestResponse(ex.getFieldName(), ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ExceptionBadRequestResponse> handleValidationException(NotFoundException ex) {
        ExceptionBadRequestResponse r = new ExceptionBadRequestResponse(null, ExceptionCode.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }
}