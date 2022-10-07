package com.pwpo.common.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private final String fieldName;
    private final ExceptionCode code;

    public ValidationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
        this.code = ExceptionCode.FIELD_VALIDATION;
    }

    public ValidationException(String message) {
        super(message);
        this.fieldName = null;
        this.code = ExceptionCode.GENERAL_VALIDATION;
    }
}
