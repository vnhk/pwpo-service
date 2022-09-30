package com.pwpo.task.dto;

import com.pwpo.user.dto.UserDTO;
import com.pwpo.user.model.ItemDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TaskSecondaryResponseDTO implements ItemDTO {
    private final Long id;
    private final String description;
    private final String estimation;
    private final UserDTO createdBy;
    private final LocalDateTime created;
    private final LocalDateTime modified;
}
