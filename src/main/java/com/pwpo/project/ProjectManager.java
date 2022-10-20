package com.pwpo.project;

import com.pwpo.common.enums.Status;
import com.pwpo.common.exception.ValidationException;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.search.model.SearchResponse;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.project.dto.EditProjectRequestDTO;
import com.pwpo.project.dto.ProjectHistoryResponseDTO;
import com.pwpo.project.dto.ProjectPrimaryResponseDTO;
import com.pwpo.project.dto.ProjectRequestDTO;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.ItemDTO;
import com.pwpo.common.model.QueryFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectManager {
    private static final int MAX_PROJECTS = 100;
    private final ItemMapper mapper;
    private final ProjectRepository projectRepository;
    private final ProjectHistoryRepository projectHistoryRepository;
    private final SearchService searchService;

    public APIResponse getProjects(SearchQueryOption options, Class<? extends ItemDTO> dtoClass) {
        SearchResponse searchResult = searchService.search(null, options);
        return mapper.mapToAPIResponse(searchResult, dtoClass);
    }

    public APIResponse getProjectById(Long id, Class<? extends ItemDTO> dtoClass) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return mapper.mapToAPIResponse(project.get(), dtoClass);
        } else {
            throw new RuntimeException("Could not find project!");
        }
    }

    public APIResponse create(ProjectRequestDTO body) {
        validate(body);
        Project project = mapper.mapToObj(body, Project.class);
        setInitValues(project);
        Project saved = projectRepository.save(project);

        return mapper.mapToAPIResponse(saved, ProjectPrimaryResponseDTO.class);
    }

    private void validate(ProjectRequestDTO body) {
        // TODO: 06.10.2022 replace with count(*)
        // TODO: 09.10.2022 in newly created project and task with added users to the project (max Tester, joe Manager),
        //  joe cannot log time - User does not have access to the project
        List<Project> projects = new ArrayList<>();
        Iterable<Project> iterable = projectRepository.findAll();
        iterable.forEach(projects::add);

        if (projects.size() >= MAX_PROJECTS) {
            throw new ValidationException("The project limit has been exceeded!");
        }

        if (projectRepository.findByName(body.getName()).isPresent()) {
            throw new ValidationException("name", "Project with the given name already exists!");
        }

        if (projectRepository.findByShortForm(body.getShortForm()).isPresent()) {
            throw new ValidationException("shortForm", "Project with the given short form already exists!");
        }
    }

    private void validateEditRequest(Optional<Project> byId, EditProjectRequestDTO body) {
        // TODO: 09.10.2022 in newly created project and task with added users to the project (max Tester, joe Manager),
        //  joe cannot log time - User does not have access to the project

        if (byId.isEmpty()) {
            throw new ValidationException("The project does not exist!");
        }

        Optional<Project> byName = projectRepository.findByName(body.getName());
        if (byName.isPresent() && isTheSameProject(body, byName)) {
            throw new ValidationException("name", "Project with the given name already exists!");
        }

        Optional<Project> byShortForm = projectRepository.findByShortForm(body.getShortForm());
        if (byShortForm.isPresent() && isTheSameProject(body, byShortForm)) {
            throw new ValidationException("shortForm", "Project with the given short form already exists!");
        }
    }

    private boolean isTheSameProject(EditProjectRequestDTO body, Optional<Project> opt) {
        return !opt.get().getId().equals(body.getId());
    }

    private void setInitValues(Project project) {
        project.setStatus(Status.NEW);
        project.setCreated(LocalDateTime.now());
    }

    @Transactional
    public APIResponse edit(EditProjectRequestDTO body) {
        Optional<Project> byId = projectRepository.findById(body.getId());
        validateEditRequest(byId, body);

        Project origProject = byId.get();
        ProjectHistory projectHistory = buildProjectHistory(origProject);
        updateProject(origProject, body);
        LocalDateTime updateTime = LocalDateTime.now();

        origProject.setUpdated(updateTime);
        projectHistory.setExpired(updateTime);

        origProject = projectRepository.save(origProject);
        projectHistoryRepository.save(projectHistory);

        return mapper.mapToAPIResponse(origProject, ProjectPrimaryResponseDTO.class);
    }

    private void updateProject(Project origProject, EditProjectRequestDTO body) {
        Project project = mapper.mapToObj(body, Project.class);

        origProject.setName(project.getName());
        origProject.setShortForm(project.getShortForm());
        origProject.setDescription(project.getDescription());
        origProject.setOwner(project.getOwner());
        origProject.setStatus(project.getStatus());
        origProject.setSummary(project.getSummary());
    }

    private ProjectHistory buildProjectHistory(Project project) {
        return ProjectHistory.builder()
                .project(project)
                .name(project.getName())
                .description(project.getDescription())
                .shortForm(project.getShortForm())
                .status(project.getStatus())
                .owner(project.getOwner().getNick())
                .summary(project.getSummary())
                .build();
    }

    public APIResponse getHistory(Long id, SearchQueryOption options) {
        options.setEntityToFind(ProjectHistory.class.getName());
        String query = String.format(QueryFormat.PROJECT_BY_ID, id);
        SearchResponse searchResult = searchService.search(query, options);

        return mapper.mapToAPIResponse(searchResult, ProjectHistoryResponseDTO.class);
    }
}
