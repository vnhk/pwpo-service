package com.pwpo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionBadRequestResponse {
    private final String field;
    private final ExceptionCode code;
    private final String message;
}
