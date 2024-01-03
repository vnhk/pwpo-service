package com.pwpo.task.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import com.pwpo.task.enums.TaskRelationshipType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class TaskRelationship {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;
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
