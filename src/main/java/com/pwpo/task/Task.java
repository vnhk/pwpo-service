package com.pwpo.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.enums.Priority;
import com.pwpo.common.enums.Status;
import com.pwpo.project.Project;
import com.pwpo.project.ToFullNameSerializer;
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
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private TaskType type;
    private String assignee;
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
    @JoinColumn(name = "task_id", nullable = false)
    @JsonIgnore
    private Project project;
}
