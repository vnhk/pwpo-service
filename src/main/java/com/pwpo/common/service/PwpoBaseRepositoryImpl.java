package com.pwpo.common.service;

import com.bervan.history.model.AbstractBaseEntity;
import com.bervan.history.model.BaseRepositoryImpl;
import com.pwpo.common.exception.ValidationException;
import com.pwpo.common.model.db.BaseEntity;
import com.pwpo.common.model.edit.Editable;
import com.pwpo.common.validator.EditProcess;
import com.pwpo.common.validator.EntityValidator;
import com.pwpo.common.validator.SaveProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Transactional
public class PwpoBaseRepositoryImpl<T extends AbstractBaseEntity<ID>, ID extends Serializable> extends BaseRepositoryImpl<T, ID> implements PwpoBaseRepository<T, ID> {
    private final EntityManager entityManager;
    private final EntityValidator validator;

    public PwpoBaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.validator = new EntityValidator();
    }

    @Override
    public <S extends T> S save(S entity) {
        validation(entity);
        return super.save(entity);
    }

    @Override
    public <S extends T> S saveWithoutHistory(S entity) {
        validation(entity);
        return super.saveWithoutHistory(entity);
    }

    private <S extends T> void validation(S entity) {
        if (entity.getId() == null) {
            validator.validate(entity, SaveProcess.class);
        } else {
            validator.validate(entity, EditProcess.class);
        }
    }

    @Override
    public <S extends T> S edit(Editable<ID> editable) {
        try {
            BaseEntity entity = getEntity(editable.getEntityId());
            LocalDateTime updateTime = LocalDateTime.now();
            entity.setUpdated(updateTime);

            return save((S) entity);
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Could not edit entity!", ex);
            throw new RuntimeException("Could not edit entity!");
        }
    }

//    @Override
//    public <S extends T> S editWithoutHistory(S entity) {
//        validator.validate(entity, EditProcess.class);
//        return super.save(entity);
//    }

    //    private <S extends T> S edit(BaseEntity entity, BaseHistoryEntity history) {
//        validator.validate(entity, EditProcess.class);
//        saveHistory(history);
//        return super.save((S) entity);
//    }
//
//    private void saveHistory(BaseHistoryEntity history) {
//        this.entityManager.persist(history);
//    }
//
//    private void update(BaseEntity entity, Editable<ID> editable) throws NoSuchFieldException, IllegalAccessException {
//        List<Field> declaredFields = getFieldsFromEditable(editable, entity);
//
//        for (Field declaredField : declaredFields) {
//            String name = declaredField.getName();
//            Field entityField = entity.getClass().getDeclaredField(name);
//            Object val = getValFromField(editable, declaredField);
//            setFieldVal(entity, entityField, val);
//        }
//    }
//
//    private void setFieldVal(Persistable entity, Field entityField, Object val) throws IllegalAccessException {
//        if (val instanceof Long && entityField.getType().getSuperclass().equals(BaseEntity.class)) {
//            //it means that we try to set ID (Long) as a BaseEntityClass ex. UserDetails owner in Project = 1L;
//            val = entityManager.find(entityField.getType(), val);
//        }
//        entityField.setAccessible(true);
//        entityField.set(entity, val);
//        entityField.setAccessible(false);
//    }
//
//    private Object getValFromField(Editable<ID> editable, Field declaredField) throws IllegalAccessException {
//        declaredField.setAccessible(true);
//        Object val = declaredField.get(editable);
//        declaredField.setAccessible(false);
//        return val;
//    }
//
//    protected List<Field> getFieldsFromEditable(Editable<ID> editable, BaseEntity entity) {
//        List<Field> parentSupperClassDeclaredFields = Arrays.stream(editable.getClass().getSuperclass().getDeclaredFields()).toList();
//        List<Field> editableFields = Arrays.stream(editable.getClass().getDeclaredFields()).collect(Collectors.toList());
//        editableFields.addAll(parentSupperClassDeclaredFields);
//
//        List<String> availableFieldsInEntity = Arrays.stream(entity.getClass().getDeclaredFields()).map(Field::getName).toList();
//
//        return editableFields.stream().filter(e -> !e.getName().equalsIgnoreCase("id"))
//                .filter(e -> availableFieldsInEntity.contains(e.getName()))
//                .collect(Collectors.toList());
//    }
//
//    private BaseHistoryEntity buildHistory(BaseEntity entity, Class<? extends BaseHistoryEntity> historyClass) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
//        BaseHistoryEntity history = historyClass.getDeclaredConstructor().newInstance();
//        List<Field> historyFields = getHistoryFields(historyClass);
//        Class<?> entityClass = entity.getClass();
//
//        for (Field historyField : historyFields) {
//            String name = historyField.getName();
//            Field entityField = entityClass.getDeclaredField(name);
//            Object val = getVal(entity, entityField, historyField);
//            setFieldVal(history, historyField, val);
//        }
//
//        setTargetEntity(history, entity);
//
//        setEditor(history);
//
//        return history;
//    }
//
//    private void setTargetEntity(BaseHistoryEntity history, BaseEntity entity) {
//        history.buildTargetEntityConnection(entity);
//    }
//
//    private void setEditor(BaseHistoryEntity history) {
//        UserAccount loggedUser = getLoggedUser();
//        history.setEditor(loggedUser);
//        loggedUser.getEdited().add(history);
//    }
//
//    private UserAccount getLoggedUser() {
//        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        return (UserAccount) entityManager.createQuery("SELECT u FROM UserAccount u where u.nick = :username ")
//                .setParameter("username", principal.getUsername())
//                .getSingleResult();
//    }
//
//    private Object getVal(Object entity, Field entityField, Field historyField) throws IllegalAccessException, NoSuchFieldException {
//        String path = historyField.getAnnotation(HistoryField.class).savePath();
//        if (Strings.isNotBlank(path)) {
//            String[] values = path.split("\\.");
//            //example in Project: User owner->owner.nick
//            for (String value : values) {
//                entityField.setAccessible(true);
//                entity = entityField.get(entity);
//                entityField.setAccessible(false);
//                entityField = entityField.getType().getDeclaredField(value);
//            }
//        }
//        Object res = null;
//        if(entity != null) {
//            entityField.setAccessible(true);
//            res = entityField.get(entity);
//            entityField.setAccessible(false);
//        }
//
//        return res;
//    }
//
//
//    private List<Field> getHistoryFields(Class<? extends BaseHistoryEntity> historyClass) {
//        return Arrays.stream(historyClass.getDeclaredFields())
//                .filter(e -> e.isAnnotationPresent(HistoryField.class))
//                .filter(e -> !e.getAnnotation(HistoryField.class).isTargetEntity())
//                .collect(Collectors.toList());
//    }
//
    private BaseEntity getEntity(ID id) {
        Optional byId = findById(id);

        if (byId.isEmpty()) {
            throw new ValidationException("The entity does not exist!");
        }

        return (BaseEntity) byId.get();
    }
}
