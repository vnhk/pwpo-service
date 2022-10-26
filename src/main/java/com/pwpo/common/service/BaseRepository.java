package com.pwpo.common.service;

import com.pwpo.common.model.edit.Editable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    <S extends T> S edit(Editable<ID> editable);
}
