package com.pwpo.project.dto;

import com.pwpo.user.dto.UserDTO;
import com.pwpo.user.model.ItemDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ProjectSecondaryResponseDTO implements ItemDTO {
    private final Long id;
    private final String description;
    private final UserDTO createdBy;
    private final LocalDateTime created;
    private final LocalDateTime modified;
}
