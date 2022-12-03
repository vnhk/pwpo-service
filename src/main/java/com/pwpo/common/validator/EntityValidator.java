package com.pwpo.common.validator;

import com.pwpo.common.model.db.BaseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

@Service
public class EntityValidator {
    private final Validator validator;

    public EntityValidator() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public void validate(BaseEntity entity, Class<? extends ValidationProcess> process) {
        Set<ConstraintViolation<BaseEntity>> validateDefault = validator.validate(entity);
        Set<ConstraintViolation<BaseEntity>> validateByProcess = validator.validate(entity, process);


        Set<ConstraintViolation<BaseEntity>> result = new HashSet<>();
        result.addAll(validateDefault);
        result.addAll(validateByProcess);

        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }
}
