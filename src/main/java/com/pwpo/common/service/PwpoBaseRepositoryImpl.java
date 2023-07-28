package com.pwpo.common.service;

import com.bervan.history.model.AbstractBaseEntity;
import com.bervan.history.model.AbstractBaseHistoryEntity;
import com.bervan.history.model.BaseRepositoryImpl;
import com.bervan.history.model.Persistable;
import com.pwpo.common.exception.ValidationException;
import com.pwpo.common.model.db.BaseEntity;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.model.edit.Editable;
import com.pwpo.common.validator.EditProcess;
import com.pwpo.common.validator.EntityValidator;
import com.pwpo.common.validator.SaveProcess;
import com.pwpo.task.model.TaskHistory;
import com.pwpo.user.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return super.save(((S) entity));
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
            update(entity, editable);
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

    @Override
    protected void historyPreHistorySave(AbstractBaseHistoryEntity<ID> history) {
        ((BaseHistoryEntity) history).setEditor(getLoggedUser());
        super.historyPreHistorySave(history);
    }

    private UserAccount getLoggedUser() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return (UserAccount) entityManager.createQuery("SELECT u FROM UserAccount u where u.nick = :username ")
                .setParameter("username", principal.getUsername())
                .getSingleResult();
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

    private BaseEntity getEntity(ID id) {
        Optional byId = findById(id);

        if (byId.isEmpty()) {
            throw new ValidationException("The entity does not exist!");
        }

        return (BaseEntity) byId.get();
    }
}
