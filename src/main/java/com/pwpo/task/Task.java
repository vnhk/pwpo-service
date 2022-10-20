package com.pwpo.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.deserializer.FromProjectIdDeserializer;
import com.pwpo.common.deserializer.FromUserIdDeserializer;
import com.pwpo.common.enums.Priority;
import com.pwpo.common.enums.Status;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import com.pwpo.project.Project;
import com.pwpo.task.enums.TaskType;
import com.pwpo.task.timelog.TimeLog;
import com.pwpo.user.UserDetails;
import com.pwpo.common.model.BaseEntity;
import com.pwpo.common.model.Constants;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Task extends BaseEntity {
    @Column(unique = true)
    private String number;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private TaskType type;
    @ManyToOne
    @JsonDeserialize(using = FromUserIdDeserializer.class)
    private UserDetails assignee;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private Status status;
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private Priority priority;
    private String summary;
    @Column(length = Constants.DESCRIPTION_MAX)
    private String description;
    @ManyToOne
    @JsonDeserialize(using = FromUserIdDeserializer.class)
    private UserDetails owner;
    @ManyToOne
    private UserDetails createdBy;
    private Integer estimation;
    @ManyToOne
    @JsonDeserialize(using = FromProjectIdDeserializer.class)
    private Project project;
    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private List<TimeLog> timeLogs;
}
