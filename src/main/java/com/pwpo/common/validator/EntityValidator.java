package com.pwpo.common.validator;

import com.bervan.history.model.AbstractBaseEntity;
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

    public void validate(AbstractBaseEntity entity, Class<? extends ValidationProcess> process) {
        Set<ConstraintViolation<AbstractBaseEntity>> validateDefault = validator.validate(entity);
        Set<ConstraintViolation<AbstractBaseEntity>> validateByProcess = validator.validate(entity, process);


        Set<ConstraintViolation<AbstractBaseEntity>> result = new HashSet<>();
        result.addAll(validateDefault);
        result.addAll(validateByProcess);

        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }
}
