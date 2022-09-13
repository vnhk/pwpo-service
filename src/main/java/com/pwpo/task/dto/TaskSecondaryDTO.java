package com.pwpo.task.dto;

import com.pwpo.common.model.ItemDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TaskSecondaryDTO implements ItemDTO {
    private final Long id;
    private final String description;
    private final String estimation;
    private final String createdBy;
    private final LocalDateTime created;
    private final LocalDateTime modified;
}
