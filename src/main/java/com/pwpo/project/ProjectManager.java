package com.pwpo.project;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectManager {

    public ProjectResponse getProjects() {
        return getMockProjects();
    }

    private ProjectResponse getMockProjects() {
        List<Project> mockProjects = new ArrayList<>();
        int amount = 40;
        for (int i = 0; i < amount; i++) {
            mockProjects.add(getProjectMock(i));
        }


        return new ProjectResponse(mockProjects, amount);
    }

    private static Project getProjectMock(int i) {
        Project project = new Project();
        project.setName("Test Name" + i);
        project.setDescription("Test Description" + i);
        project.setDeleted(false);
        project.setCreated(LocalDateTime.now());
        project.setModified(LocalDateTime.now());
        project.setCreatedBy("Test Created By" + i);
        project.setStatus("Test Status" + i);
        project.setOwner("Test Owner" + i);
        project.setSummary("Test Summary" + i);
        project.setShortForm("Test Short Form" + i);

        return project;
    }
}
