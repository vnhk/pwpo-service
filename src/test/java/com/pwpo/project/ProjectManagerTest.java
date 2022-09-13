package com.pwpo.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwpo.APICollectionResponse;
import com.pwpo.ItemDTO;
import com.pwpo.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProjectManagerTest {
    private ProjectManager projectManager;
    @Mock
    private ProjectRepository projectRepository;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = TestUtils.getObjectMapper();
        projectManager = new ProjectManager(objectMapper, projectRepository);
    }

    @Test
    void getProjectsPrimary() {
        List<Project> allProjects = new ArrayList<>();
        Project project1 = Project.builder().id(1L).build();
        Project project2 = Project.builder().id(2L).build();
        Project project3 = Project.builder().id(3L).build();
        allProjects.add(project1);
        allProjects.add(project2);
        allProjects.add(project3);

        when(projectRepository.findAll()).thenReturn(allProjects);
        APICollectionResponse projects = projectManager.getProjects(ProjectPrimaryDTO.class);

        assertThat(projects.getTotalCount()).isEqualTo(3);
        assertThat(projects.getItems()).hasSize(3);
        assertThat(projects.getItems().get(0) instanceof ProjectPrimaryDTO).isTrue();
        assertThat(projects.getItems().get(1) instanceof ProjectPrimaryDTO).isTrue();
        assertThat(projects.getItems().get(2) instanceof ProjectPrimaryDTO).isTrue();

        assertThat(((ProjectPrimaryDTO) projects.getItems().get(0)).getId()).isEqualTo(1L);
        assertThat(((ProjectPrimaryDTO) projects.getItems().get(1)).getId()).isEqualTo(2L);
        assertThat(((ProjectPrimaryDTO) projects.getItems().get(2)).getId()).isEqualTo(3L);
    }

    @Test
    void getProjectsSecondary() {
        List<Project> allProjects = new ArrayList<>();
        Project project1 = Project.builder().description("desc1").build();
        Project project2 = Project.builder().description("desc2").build();
        Project project3 = Project.builder().description("desc3").build();
        allProjects.add(project1);
        allProjects.add(project2);
        allProjects.add(project3);

        when(projectRepository.findAll()).thenReturn(allProjects);
        APICollectionResponse projects = projectManager.getProjects(ProjectSecondaryDTO.class);

        assertThat(projects.getTotalCount()).isEqualTo(3);
        assertThat(projects.getItems()).hasSize(3);
        assertThat(projects.getItems().get(0) instanceof ProjectSecondaryDTO).isTrue();
        assertThat(projects.getItems().get(1) instanceof ProjectSecondaryDTO).isTrue();
        assertThat(projects.getItems().get(2) instanceof ProjectSecondaryDTO).isTrue();

        assertThat(((ProjectSecondaryDTO) projects.getItems().get(0)).getDescription()).isEqualTo("desc1");
        assertThat(((ProjectSecondaryDTO) projects.getItems().get(1)).getDescription()).isEqualTo("desc2");
        assertThat(((ProjectSecondaryDTO) projects.getItems().get(2)).getDescription()).isEqualTo("desc3");
    }

    @Test
    void getProjectByIdPrimary() {
        Project project1 = Project.builder().id(1L).name("name1").build();

        when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(project1));
        ItemDTO projectById = projectManager.getProjectById("1", ProjectPrimaryDTO.class);

        assertThat(projectById instanceof ProjectPrimaryDTO).isTrue();
        assertThat(((ProjectPrimaryDTO) projectById).getName()).isEqualTo("name1");
        assertThat(((ProjectPrimaryDTO) projectById).getId()).isEqualTo(1L);
    }

    @Test
    void getProjectWhenNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> projectManager.getProjectById("1", ProjectPrimaryDTO.class))
                .withMessage("Could not find project!");
    }

    @Test
    void getProjectWhenCouldNotMapProject() {
        objectMapper = new ObjectMapper();
        projectManager = new ProjectManager(objectMapper, projectRepository);

        Project project1 = Project.builder().id(1L).name("name1").build();

        when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(project1));

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> projectManager.getProjectById("1", ProjectPrimaryDTO.class))
                .withMessage("Could not map project!");
    }

    @Test
    void getProjectByIdSecondary() {
        Project project1 = Project.builder().id(1L).description("desc1").build();

        when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(project1));
        ItemDTO projectById = projectManager.getProjectById("1", ProjectSecondaryDTO.class);

        assertThat(projectById instanceof ProjectSecondaryDTO).isTrue();
        assertThat(((ProjectSecondaryDTO) projectById).getDescription()).isEqualTo("desc1");
        assertThat(((ProjectSecondaryDTO) projectById).getId()).isEqualTo(1L);
    }
}