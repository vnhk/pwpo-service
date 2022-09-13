package com.pwpo.project.dto;

import com.pwpo.common.model.ItemDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProjectPrimaryDTO implements ItemDTO {
    private final Long id;
    private final String name;
    private final String summary;
    private final String status;
    private final String shortForm;
    private final String owner;
}
