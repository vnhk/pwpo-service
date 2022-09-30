package com.pwpo.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwpo.TestUtils;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.search.model.SearchResponse;
import com.pwpo.common.search.model.SortDirection;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.project.dto.ProjectPrimaryDTO;
import com.pwpo.project.dto.ProjectSecondaryDTO;
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
    @Mock
    private SearchService searchService;
    private ItemMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ItemMapper(TestUtils.getObjectMapper());
        projectManager = new ProjectManager(mapper, projectRepository, searchService);
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

        SearchQueryOption options = new SearchQueryOption(SortDirection.DESC, "id", 1, 10, Project.class.getName());
        SearchResponse searchResponse = new SearchResponse(allProjects, allProjects.size(), 1, allProjects.size());

        when(searchService.search(null, options)).thenReturn(searchResponse);
        APIResponse projects = projectManager.getProjects(options, ProjectPrimaryDTO.class);

        assertThat(projects.getCurrentFound()).isEqualTo(3);
        assertThat(projects.getAllFound()).isEqualTo(3);
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

        SearchQueryOption options = new SearchQueryOption(SortDirection.DESC, "id", 1, 10, Project.class.getName());
        SearchResponse searchResponse = new SearchResponse(allProjects, allProjects.size(), 1, allProjects.size());

        when(searchService.search(null, options)).thenReturn(searchResponse);
        APIResponse projects = projectManager.getProjects(options, ProjectSecondaryDTO.class);

        assertThat(projects.getCurrentFound()).isEqualTo(3);
        assertThat(projects.getItems()).hasSize(3);
        assertThat(projects.getAllFound()).isEqualTo(3);
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
        APIResponse projectByIdResponse = projectManager.getProjectById("1", ProjectPrimaryDTO.class);

        assertThat(projectByIdResponse.getItems()).hasSize(1);
        ItemDTO projectById = projectByIdResponse.getItems().get(0);
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
        mapper = new ItemMapper(new ObjectMapper());
        projectManager = new ProjectManager(mapper, projectRepository, searchService);

        Project project1 = Project.builder().id(1L).name("name1").build();

        when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(project1));

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> projectManager.getProjectById("1", ProjectPrimaryDTO.class))
                .withMessage("Could not map item!");
    }

    @Test
    void getProjectByIdSecondary() {
        Project project1 = Project.builder().id(1L).description("desc1").build();

        when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(project1));
        APIResponse projectByIdResponse = projectManager.getProjectById("1", ProjectSecondaryDTO.class);

        assertThat(projectByIdResponse.getItems()).hasSize(1);
        ItemDTO projectById = projectByIdResponse.getItems().get(0);
        assertThat(projectById instanceof ProjectSecondaryDTO).isTrue();
        assertThat(((ProjectSecondaryDTO) projectById).getDescription()).isEqualTo("desc1");
        assertThat(((ProjectSecondaryDTO) projectById).getId()).isEqualTo(1L);
    }
}