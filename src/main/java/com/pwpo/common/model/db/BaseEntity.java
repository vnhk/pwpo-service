package com.pwpo.common.model.db;

import com.bervan.history.model.AbstractBaseEntity;
import com.pwpo.common.model.Constants;
import com.pwpo.common.validator.EditProcess;
import com.pwpo.common.validator.SaveProcess;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable, AbstractBaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constants.DB_SEQUENCE)
    @SequenceGenerator(name = Constants.DB_SEQUENCE, initialValue = Constants.DB_SEQUENCE_INIT)
    @Null(groups = SaveProcess.class)
    @NotNull(groups = EditProcess.class)
    protected Long id;
    @UpdateTimestamp
    protected LocalDateTime updated;
    @CreationTimestamp
    protected LocalDateTime created;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
