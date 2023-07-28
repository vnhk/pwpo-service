package com.pwpo.common.service;

import com.bervan.history.model.BaseRepository;
import com.pwpo.common.model.edit.Editable;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface PwpoBaseRepository<T, ID extends Serializable> extends BaseRepository<T, ID> {
    <S extends T> S edit(Editable<ID> editable);
}
