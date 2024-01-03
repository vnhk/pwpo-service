package com.pwpo.task.dto;

import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.task.enums.TaskRelationshipType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskChildResponseDTO implements ItemDTO {
    private final Long taskId;
    private final TaskRelationshipType type;
    private final TaskPrimaryResponseDTO subTask;
}
