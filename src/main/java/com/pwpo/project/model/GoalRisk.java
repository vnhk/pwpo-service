package com.pwpo.project.model;

import com.pwpo.common.model.Constants;
import com.pwpo.common.model.db.BaseEntity;
import com.pwpo.common.model.db.Persistable;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GoalRisk extends BaseEntity {
    @Length(min = 5, max = 500)
    private String content;
    @Min(1)
    @Max(5)
    private Integer priority;
    @Enumerated(EnumType.STRING)
    @NotNull
    private GoalRiskType type;
    @ManyToOne
    @NotNull
    private Project project;
}
