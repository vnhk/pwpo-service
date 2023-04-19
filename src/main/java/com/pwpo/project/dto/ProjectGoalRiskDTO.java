package com.pwpo.project.dto;

import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.user.dto.UserDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProjectGoalRiskDTO implements ItemDTO {
    private final Long id;
    private final String value;
    private final Integer priority;
}
