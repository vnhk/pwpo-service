package com.pwpo.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.deserializer.FromProjectIdDeserializer;
import com.pwpo.common.deserializer.FromUserIdDeserializer;
import com.pwpo.common.enums.Priority;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.AttachmentHandler;
import com.pwpo.common.model.Constants;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import com.pwpo.project.model.Project;
import com.pwpo.task.enums.TaskType;
import com.pwpo.task.timelog.TimeLog;
import com.pwpo.user.UserAccount;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Task extends AttachmentHandler {
    @NotNull
    @Size(min = 1, max = Constants.NUMBER_MAX)
    @Column(unique = true, length = Constants.NUMBER_MAX)
    private String number;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private TaskType type;

    @ManyToOne
    @JsonDeserialize(using = FromUserIdDeserializer.class)
    private UserAccount assignee;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private Status status;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private Priority priority;

    @NotNull
    @Size(min = 1, max = Constants.SUMMARY_MAX)
    @Column(length = Constants.SUMMARY_MAX)
    private String summary;

    @Size(max = Constants.DESCRIPTION_MAX)
    @Column(length = Constants.DESCRIPTION_MAX)
    private String description;

    @ManyToOne
    @JsonDeserialize(using = FromUserIdDeserializer.class)
    @NotNull
    private UserAccount owner;

    @ManyToOne
    private UserAccount createdBy;

    @Max(value = Constants.ESTIMATION_MAX)
    @Min(value = Constants.ESTIMATION_MIN)
    private Integer estimation;

    @ManyToOne
    @JsonDeserialize(using = FromProjectIdDeserializer.class)
    @NotNull
    private Project project;

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private List<TimeLog> timeLogs;

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private List<TaskHistory> history;

    @Override
    @JsonIgnore
    public List getHistoryEntities() {
        return history;
    }
}
