package com.pwpo.project.dto;

import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.user.dto.UserDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProjectPrimaryResponseDTO implements ItemDTO {
    private final Long id;
    private final String name;
    private final String shortForm;
    private final String summary;
    private final String status;
    private final UserDTO owner;
}
