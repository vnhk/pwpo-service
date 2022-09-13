package com.pwpo.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.enums.Priority;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.Itemable;
import com.pwpo.common.serializer.ToFullNameSerializer;
import com.pwpo.project.Project;
import com.pwpo.task.enums.TaskType;
import com.pwpo.user.UserDetails;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Itemable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private TaskType type;
    @ManyToOne
    @JsonSerialize(using = ToFullNameSerializer.class)
    private UserDetails assignee;
    private Status status;
    private LocalDateTime dueDate;
    private Priority priority;
    private String summary;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    @ManyToOne
    @JsonSerialize(using = ToFullNameSerializer.class)
    private UserDetails owner;
    @ManyToOne
    @JsonSerialize(using = ToFullNameSerializer.class)
    private UserDetails createdBy;
    private String estimation;
    private boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;
}
