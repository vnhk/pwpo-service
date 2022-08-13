package com.pwpo.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProjectResponse {
    private List<Project> projects;
    private int totalCount;
}
