package com.pwpo.project;

import com.pwpo.ItemDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ProjectSecondaryDTO implements ItemDTO {
    private final Long id;
    private final String description;
    private final String createdBy;
    private final LocalDateTime created;
    private final LocalDateTime modified;
}
