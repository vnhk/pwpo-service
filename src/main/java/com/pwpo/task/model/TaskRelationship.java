package com.pwpo.task.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.model.db.BaseEntity;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import com.pwpo.task.enums.TaskRelationshipType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TaskRelationship extends BaseEntity {
    @ManyToOne
    @NotNull
    private Task parent;
    @ManyToOne
    @NotNull
    private Task child;
    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private TaskRelationshipType type;
}
