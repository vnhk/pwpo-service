package com.pwpo.task;

import com.pwpo.ItemDTO;
import com.pwpo.common.enums.Priority;
import com.pwpo.common.enums.Status;
import com.pwpo.task.enums.TaskType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TaskPrimaryDTO implements ItemDTO {
    private final Long id;
    private final TaskType type;
    private final String number;
    private final String summary;
    private final Status status;
    private final String assignee;
    private final LocalDateTime dueDate;
    private final Priority priority;
}
