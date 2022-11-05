package com.pwpo.common.service;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.db.Persistable;
import com.pwpo.common.model.edit.Editable;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseService<T extends Persistable, ID extends Serializable> {
    private final BaseRepository<T, ID> repository;
    private final ItemMapper mapper;

    @Transactional
    public APIResponse edit(Editable<ID> body) {
        Optional<T> orig = repository.findById(body.getEntityId());
        validateEditRequest(orig, body);

        T edited = repository.edit(body);

        return mapper.mapToAPIResponse(edited, body.getResponseDTO());
    }

    protected abstract void validateEditRequest(Optional<T> orig, Editable<ID> body);
}
