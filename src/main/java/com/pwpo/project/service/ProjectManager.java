package com.pwpo.project.service;

import com.pwpo.common.enums.Status;
import com.pwpo.common.exception.ValidationException;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.model.edit.Editable;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.search.model.SearchResponse;
import com.pwpo.common.service.BaseService;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.project.Project;
import com.pwpo.project.ProjectRepository;
import com.pwpo.project.dto.EditProjectRequestDTO;
import com.pwpo.project.dto.ProjectPrimaryResponseDTO;
import com.pwpo.project.dto.ProjectRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectManager extends BaseService<Project, Long> {
    private static final int MAX_PROJECTS = 100;
    private final ItemMapper mapper;
    private final ProjectRepository projectRepository;
    private final SearchService searchService;

    public ProjectManager(ProjectRepository repository, ItemMapper mapper, SearchService searchService) {
        super(repository, mapper);
        this.projectRepository = repository;
        this.mapper = mapper;
        this.searchService = searchService;
    }

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

    private boolean isTheSameProject(EditProjectRequestDTO<Long> body, Optional<Project> opt) {
        return !opt.get().getId().equals(body.getEntityId());
    }

    private void setInitValues(Project project) {
        project.setStatus(Status.NEW);
        project.setCreated(LocalDateTime.now());
    }

    @Override
    protected void validateEditRequest(Optional<Project> orig, Editable<Long> body) {
        // TODO: 09.10.2022 in newly created project and task with added users to the project (max Tester, joe Manager),
        //  joe cannot log time - User does not have access to the project

        EditProjectRequestDTO<Long> request = (EditProjectRequestDTO<Long>) body;

        if (orig.isEmpty()) {
            throw new ValidationException("The project does not exist!");
        }

        Optional<Project> byName = projectRepository.findByName(request.getName());
        if (byName.isPresent() && isTheSameProject(request, byName)) {
            throw new ValidationException("name", "Project with the given name already exists!");
        }

        Optional<Project> byShortForm = projectRepository.findByShortForm(request.getShortForm());
        if (byShortForm.isPresent() && isTheSameProject(request, byShortForm)) {
            throw new ValidationException("shortForm", "Project with the given short form already exists!");
        }
    }
}
