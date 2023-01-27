package com.pwpo.common.service;

import com.pwpo.common.exception.ApiError;
import com.pwpo.common.exception.IntegrityValidationException;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.db.Persistable;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.model.edit.Editable;
import com.pwpo.common.validator.EntitySaveIntegrityValidation;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseService<T extends Persistable, ID extends Serializable> {
    private final BaseRepository<T, ID> repository;
    private final ItemMapper mapper;
    private final List<? extends EntitySaveIntegrityValidation<T>> validations;

    public APIResponse edit(Editable<ID> body) {
        Optional<T> orig = repository.findById(body.getEntityId());
        validateEditRequest(orig, body);

        T edited = repository.edit(body);

        return mapper.mapToAPIResponse(edited, body.getResponseDTO());
    }

    public APIResponse<? extends ItemDTO> create(ItemDTO body, Class<? extends ItemDTO> responseType) {
        T entity = mapDTO(body);
        validateSaveRequest(entity);
        preSave(entity);
        T saved = repository.save(entity);
        postSave(entity);

        return mapper.mapToAPIResponse(saved, responseType);
    }

    public boolean exist(ID id) {
        return repository.findById(id).isPresent();
    }

    protected abstract void postSave(T entity);

    protected abstract void preSave(T entity);

    protected abstract T mapDTO(ItemDTO body);

    protected void validateEditRequest(Optional<T> orig, Editable<ID> body) {

    }

    protected void validateSaveRequest(T entity) {
        List<ApiError> errors = new ArrayList<>();

        for (EntitySaveIntegrityValidation<T> validation : validations) {
            errors.addAll(validation.validate(entity));
        }

        if (errors.size() > 0) {
            throw new IntegrityValidationException(errors);
        }
    }
}
