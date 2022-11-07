package com.pwpo.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.enums.Priority;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.Constants;
import com.pwpo.common.model.HistoryField;
import com.pwpo.common.model.db.BaseEntity;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import com.pwpo.task.enums.TaskType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TaskHistory extends BaseHistoryEntity {
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    @HistoryField(comparePath = "displayName")
    private TaskType type;
    @HistoryField(savePath = "nick", comparePath = "nick")
    private String assignee;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    @HistoryField(comparePath = "displayName")
    private Status status;
    @HistoryField
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    @HistoryField(comparePath = "displayName")
    private Priority priority;
    @HistoryField
    private String summary;
    @Column(length = Constants.DESCRIPTION_MAX)
    @HistoryField
    private String description;
    @HistoryField(savePath = "nick", comparePath = "nick")
    private String owner;
    @HistoryField
    private Integer estimation;
    @ManyToOne
    @JsonIgnore
    @HistoryField(comparable = false, isTargetEntity = true)
    private Task task;

    @Override
    public void buildTargetEntityConnection(BaseEntity entity) {
        this.task = (Task) entity;
        task.getHistoryEntities().add(this);
    }

    @Override
    @JsonIgnore
    public BaseEntity getTargetEntity() {
        return task;
    }
}