package com.pwpo.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
    private String field;
    private ExceptionCode code;
    private String message;

    public ApiError() {

    }

    public ApiError(ExceptionCode code, String message) {
        this.field = null;
        this.code = code;
        this.message = message;
    }

    public ApiError(String field, ExceptionCode code, String message) {
        this.field = field;
        this.code = code;
        this.message = message;
    }
}
