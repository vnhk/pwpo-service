package com.pwpo.task.model;

import com.bervan.history.model.HistoryField;
import com.bervan.history.model.HistoryOwnerEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.enums.Priority;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.Constants;
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
public class TaskHistory extends BaseHistoryEntity {
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    @HistoryField(comparePath = "displayName")
    private TaskType type;
    @HistoryField(savePath = "assignee.nick", comparePath = "nick")
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
    @Lob
    private String description;
    @HistoryField(savePath = "owner.nick", comparePath = "nick")
    private String owner;
    @HistoryField
    private Integer estimation;
    @ManyToOne
    @JsonIgnore
    @HistoryOwnerEntity
    private Task task;
}