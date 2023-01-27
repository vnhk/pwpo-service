package com.pwpo.common.validator;

import com.pwpo.common.exception.ApiError;
import com.pwpo.common.model.db.Persistable;

import java.util.List;

public interface EntitySaveIntegrityValidation<T extends Persistable> {
    List<ApiError> validate(T entity);
}
