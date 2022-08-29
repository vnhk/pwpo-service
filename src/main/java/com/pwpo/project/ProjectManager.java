package com.pwpo.project;

import com.pwpo.APIResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectManager {

    public APIResponse getProjects() {
        return getMockProjectsDTO();
    }

    private APIResponse getMockProjectsDTO() {
        List<ProjectDTO> mockProjects = new ArrayList<>();
        int amount = 40;
        for (int i = 0; i < amount; i++) {
            mockProjects.add(getProjectDTOMock(i));
        }

        return new APIResponse(mockProjects, amount);
    }

    private ProjectDTO getProjectDTOMock(int i) {
        return ProjectDTO
                .builder()
                .id((long) i)
                .name("Test Name" + i)
                .description("Test Description" + i)
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .createdBy("Test Created By" + i)
                .status("Test Status" + i)
                .owner("Test Owner" + i)
                .summary("Test Summary" + i)
                .shortForm("Test Short Form" + i)
                .build();
    }

    public ProjectDTO getProjectById(String id) {
        return getProjectDTOMock(Integer.parseInt(id));
    }
}
