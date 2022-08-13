package com.pwpo.project;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Project {
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
    private boolean isDeleted;
}
