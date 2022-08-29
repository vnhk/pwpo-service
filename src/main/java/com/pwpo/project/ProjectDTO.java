package com.pwpo.project;

import com.pwpo.ItemDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ProjectDTO implements ItemDTO {
    private Long id;
    private String name;
    private String summary;
    private String status;
    private String description;
    private String shortForm;
    private String owner;
    private String createdBy;
    private LocalDateTime created;
    private LocalDateTime modified;
}
