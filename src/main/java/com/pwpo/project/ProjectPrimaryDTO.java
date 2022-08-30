package com.pwpo.project;

import com.pwpo.ItemDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ProjectPrimaryDTO implements ItemDTO {
    private final Long id;
    private final String name;
    private final String summary;
    private final String status;
    private final String shortForm;
    private final String owner;
}
