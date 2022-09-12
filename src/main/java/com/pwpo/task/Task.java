package com.pwpo.task;

import com.pwpo.common.enums.Priority;
import com.pwpo.project.Project;
import com.pwpo.common.enums.Status;
import com.pwpo.task.enums.TaskType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Task {
    private Long id;
    private String number;
    private TaskType type;
    private String assignee;
    private Status status;
    private LocalDateTime dueDate;
    private Project project;
    private Priority priority;
    private String summary;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String createdBy;
    private String estimation;
    private boolean isDeleted;
}
