package com.pwpo.common.validator;

import com.pwpo.common.exception.ApiError;
import com.bervan.history.model.Persistable;

import java.util.List;

public interface EntitySaveIntegrityValidation<T extends Persistable> {
    List<ApiError> validate(T entity);
}
