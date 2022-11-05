package com.pwpo.common.controller;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.db.Persistable;
import com.pwpo.common.model.edit.Editable;
import com.pwpo.common.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;

@RequiredArgsConstructor
public abstract class BaseEntityController<T extends Persistable, ID extends Serializable> {
    private final BaseService<T, ID> service;

    protected ResponseEntity<APIResponse> edit(@Valid @RequestBody Editable<ID> body) {
        return new ResponseEntity<>(service.edit(body), HttpStatus.OK);
    }
}
