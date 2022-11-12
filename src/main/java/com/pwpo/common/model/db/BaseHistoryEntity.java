package com.pwpo.common.model.db;

import com.pwpo.common.model.Constants;
import com.pwpo.user.UserDetails;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class BaseHistoryEntity implements Persistable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constants.DB_SEQUENCE)
    @SequenceGenerator(name = Constants.DB_SEQUENCE, initialValue = Constants.DB_SEQUENCE_INIT)
    protected Long id;
    protected LocalDateTime expired;
    @ManyToOne
    protected UserDetails editor;

    public void buildTargetEntityConnection(BaseEntity entity) {

    }

    public BaseEntity getTargetEntity() {
        return null;
    }
}
