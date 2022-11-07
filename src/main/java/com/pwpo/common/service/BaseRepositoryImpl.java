package com.pwpo.common.service;

import com.pwpo.common.exception.ValidationException;
import com.pwpo.common.model.HistoryField;
import com.pwpo.common.model.db.BaseEntity;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.model.db.Persistable;
import com.pwpo.common.model.edit.Editable;
import com.pwpo.user.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class BaseRepositoryImpl<T extends BaseEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    private EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public <S extends T> S edit(Editable<ID> editable) {
        try {
            Optional<? extends BaseEntity> byId = findById(editable.getEntityId());
            validateEdit(byId);
            BaseEntity entity = byId.get();
            BaseHistoryEntity history = buildHistory(entity, editable.getHistoryClass());
            update(entity, editable);

            LocalDateTime updateTime = LocalDateTime.now();

            entity.setUpdated(updateTime);
            history.setExpired(updateTime);

            saveHistory(history);

            return super.save((S) entity);
        } catch (Exception ex) {
            log.error("Could not edit entity!", ex);
            throw new RuntimeException("Could not edit entity!");
        }
    }

    private void saveHistory(BaseHistoryEntity history) {
        this.entityManager.persist(history);
    }

    private void update(BaseEntity entity, Editable<ID> editable) throws NoSuchFieldException, IllegalAccessException {
        List<Field> declaredFields = getFieldsFromEditable(editable, entity);

        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            Field entityField = entity.getClass().getDeclaredField(name);
            Object val = getValFromField(editable, declaredField);
            setFieldVal(entity, entityField, val);
        }
    }

    private void setFieldVal(Persistable entity, Field entityField, Object val) throws IllegalAccessException {
        if (val instanceof Long && entityField.getType().getSuperclass().equals(BaseEntity.class)) {
            //it means that we try to set ID (Long) as a BaseEntityClass ex. UserDetails owner in Project = 1L;
            val = entityManager.find(entityField.getType(), val);
        }
        entityField.setAccessible(true);
        entityField.set(entity, val);
        entityField.setAccessible(false);
    }

    private Object getValFromField(Editable<ID> editable, Field declaredField) throws IllegalAccessException {
        declaredField.setAccessible(true);
        Object val = declaredField.get(editable);
        declaredField.setAccessible(false);
        return val;
    }

    protected List<Field> getFieldsFromEditable(Editable<ID> editable, BaseEntity entity) {
        List<Field> parentSupperClassDeclaredFields = Arrays.stream(editable.getClass().getSuperclass().getDeclaredFields()).toList();
        List<Field> editableFields = Arrays.stream(editable.getClass().getDeclaredFields()).collect(Collectors.toList());
        editableFields.addAll(parentSupperClassDeclaredFields);

        List<String> availableFieldsInEntity = Arrays.stream(entity.getClass().getDeclaredFields()).map(Field::getName).toList();

        return editableFields.stream().filter(e -> !e.getName().equalsIgnoreCase("id"))
                .filter(e -> availableFieldsInEntity.contains(e.getName()))
                .collect(Collectors.toList());
    }

    private BaseHistoryEntity buildHistory(BaseEntity entity, Class<? extends BaseHistoryEntity> historyClass) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        BaseHistoryEntity history = historyClass.getDeclaredConstructor().newInstance();
        List<Field> historyFields = getHistoryFields(historyClass);
        Class<?> entityClass = entity.getClass();

        for (Field historyField : historyFields) {
            String name = historyField.getName();
            Field entityField = entityClass.getDeclaredField(name);
            Object val = getVal(entity, entityField, historyField);
            setFieldVal(history, historyField, val);
        }

        setTargetEntity(history, entity);

        setEditor(history);

        return history;
    }

    private void setTargetEntity(BaseHistoryEntity history, BaseEntity entity) {
        //addOneToManyConnection
        history.buildTargetEntityConnection(entity);
    }

    private void setEditor(BaseHistoryEntity history) {
        UserDetails loggedUser = getLoggedUser();
        history.setEditor(loggedUser);
        //NULLPOINTER get Edited is null need to find userDetails with entitymanager!!!
        loggedUser.getEdited().add(history);
    }

    private UserDetails getLoggedUser() {
        //should be logged user, for now hardcoded user with Id = 1;
        return entityManager.find(UserDetails.class, 1L);
    }

    private Object getVal(Object entity, Field entityField, Field historyField) throws IllegalAccessException, NoSuchFieldException {
        String path = historyField.getAnnotation(HistoryField.class).savePath();
        if (Strings.isNotBlank(path)) {
            String[] values = path.split("\\.");
            //example in Project: User owner->owner.nick
            for (String value : values) {
                entityField.setAccessible(true);
                entity = entityField.get(entity);
                entityField.setAccessible(false);
                entityField = entityField.getType().getDeclaredField(value);
            }
        }
        entityField.setAccessible(true);
        Object res = entityField.get(entity);
        entityField.setAccessible(false);

        return res;
    }


    private List<Field> getHistoryFields(Class<? extends BaseHistoryEntity> historyClass) {
        return Arrays.stream(historyClass.getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(HistoryField.class))
                .filter(e -> !e.getAnnotation(HistoryField.class).isTargetEntity())
                .collect(Collectors.toList());
    }

    private void validateEdit(Optional<? extends BaseEntity> byId) {
        if (byId.isEmpty()) {
            throw new ValidationException("The entity does not exist!");
        }
    }
}
