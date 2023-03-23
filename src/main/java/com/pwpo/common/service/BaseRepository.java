package com.pwpo.common.service;

import com.pwpo.common.model.edit.Editable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    <S extends T> S edit(Editable<ID> editable);
    <S extends T> S editWithoutHistory(S entity);

    <S extends T> S save(S entity);
}
