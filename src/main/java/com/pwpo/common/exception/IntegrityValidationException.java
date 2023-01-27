package com.pwpo.common.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class IntegrityValidationException extends RuntimeException {
    private final List<ApiError> errors;

    public IntegrityValidationException(List<ApiError> errors) {
        this.errors = errors;
    }
}
