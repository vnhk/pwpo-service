package com.pwpo.task;

import com.pwpo.task.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaskTypeDataChart {
    private final TaskType type;
    private final Long value;
}
