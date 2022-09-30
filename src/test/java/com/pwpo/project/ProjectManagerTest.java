package com.pwpo.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwpo.TestUtils;
import com.pwpo.user.model.APIResponse;
import com.pwpo.user.model.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.search.model.SearchResponse;
import com.pwpo.common.search.model.SortDirection;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.project.dto.ProjectPrimaryResponseDTO;
import com.pwpo.project.dto.ProjectSecondaryResponseDTO;
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
        APIResponse projects = projectManager.getProjects(options, ProjectPrimaryResponseDTO.class);

        assertThat(projects.getCurrentFound()).isEqualTo(3);
        assertThat(projects.getAllFound()).isEqualTo(3);
        assertThat(projects.getItems()).hasSize(3);
        assertThat(projects.getItems().get(0) instanceof ProjectPrimaryResponseDTO).isTrue();
        assertThat(projects.getItems().get(1) instanceof ProjectPrimaryResponseDTO).isTrue();
        assertThat(projects.getItems().get(2) instanceof ProjectPrimaryResponseDTO).isTrue();

        assertThat(((ProjectPrimaryResponseDTO) projects.getItems().get(0)).getId()).isEqualTo(1L);
        assertThat(((ProjectPrimaryResponseDTO) projects.getItems().get(1)).getId()).isEqualTo(2L);
        assertThat(((ProjectPrimaryResponseDTO) projects.getItems().get(2)).getId()).isEqualTo(3L);
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
        APIResponse projects = projectManager.getProjects(options, ProjectSecondaryResponseDTO.class);

        assertThat(projects.getCurrentFound()).isEqualTo(3);
        assertThat(projects.getItems()).hasSize(3);
        assertThat(projects.getAllFound()).isEqualTo(3);
        assertThat(projects.getItems().get(0) instanceof ProjectSecondaryResponseDTO).isTrue();
        assertThat(projects.getItems().get(1) instanceof ProjectSecondaryResponseDTO).isTrue();
        assertThat(projects.getItems().get(2) instanceof ProjectSecondaryResponseDTO).isTrue();

        assertThat(((ProjectSecondaryResponseDTO) projects.getItems().get(0)).getDescription()).isEqualTo("desc1");
        assertThat(((ProjectSecondaryResponseDTO) projects.getItems().get(1)).getDescription()).isEqualTo("desc2");
        assertThat(((ProjectSecondaryResponseDTO) projects.getItems().get(2)).getDescription()).isEqualTo("desc3");
    }

    @Test
    void getProjectByIdPrimary() {
        Project project1 = Project.builder().id(1L).name("name1").build();

        when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(project1));
        APIResponse projectByIdResponse = projectManager.getProjectById("1", ProjectPrimaryResponseDTO.class);

        assertThat(projectByIdResponse.getItems()).hasSize(1);
        ItemDTO projectById = projectByIdResponse.getItems().get(0);
        assertThat(projectById instanceof ProjectPrimaryResponseDTO).isTrue();
        assertThat(((ProjectPrimaryResponseDTO) projectById).getName()).isEqualTo("name1");
        assertThat(((ProjectPrimaryResponseDTO) projectById).getId()).isEqualTo(1L);
    }

    @Test
    void getProjectWhenNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> projectManager.getProjectById("1", ProjectPrimaryResponseDTO.class))
                .withMessage("Could not find project!");
    }

    @Test
    void getProjectWhenCouldNotMapProject() {
        mapper = new ItemMapper(new ObjectMapper());
        projectManager = new ProjectManager(mapper, projectRepository, searchService);

        Project project1 = Project.builder().id(1L).name("name1").build();

        when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(project1));

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> projectManager.getProjectById("1", ProjectPrimaryResponseDTO.class))
                .withMessage("Could not map item!");
    }

    @Test
    void getProjectByIdSecondary() {
        Project project1 = Project.builder().id(1L).description("desc1").build();

        when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(project1));
        APIResponse projectByIdResponse = projectManager.getProjectById("1", ProjectSecondaryResponseDTO.class);

        assertThat(projectByIdResponse.getItems()).hasSize(1);
        ItemDTO projectById = projectByIdResponse.getItems().get(0);
        assertThat(projectById instanceof ProjectSecondaryResponseDTO).isTrue();
        assertThat(((ProjectSecondaryResponseDTO) projectById).getDescription()).isEqualTo("desc1");
        assertThat(((ProjectSecondaryResponseDTO) projectById).getId()).isEqualTo(1L);
    }
}