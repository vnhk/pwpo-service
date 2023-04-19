package com.pwpo.project.model;

import com.pwpo.common.model.Constants;
import com.pwpo.common.model.db.Persistable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
public class GoalRisk implements Persistable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constants.GOAL_RISK_SEQUENCE)
    @SequenceGenerator(name = Constants.GOAL_RISK_SEQUENCE, initialValue = Constants.DB_SEQUENCE_INIT)
    private Long id;

    @Length(min = 5, max = 100)
    private String value;
    @Size(min = 1, max = 5)
    private Integer priority;

    @ManyToOne
    private Project project;
}
