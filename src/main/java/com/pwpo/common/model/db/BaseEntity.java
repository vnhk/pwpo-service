package com.pwpo.common.model.db;

import com.pwpo.common.model.Constants;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Persistable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constants.DB_SEQUENCE)
    @SequenceGenerator(name = Constants.DB_SEQUENCE, initialValue = Constants.DB_SEQUENCE_INIT)
    protected Long id;
    @UpdateTimestamp
    protected LocalDateTime updated;
    @CreationTimestamp
    protected LocalDateTime created;

    public List<? extends Persistable> getHistoryEntities() {
        //change to abstract!!
        return new ArrayList<>();
    }
}
