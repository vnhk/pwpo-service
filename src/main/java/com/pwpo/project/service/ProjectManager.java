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
import com.pwpo.common.validator.EntitySaveIntegrityValidation;
import com.pwpo.project.dto.EditProjectRequestDTO;
import com.pwpo.project.model.Project;
import com.pwpo.project.repository.ProjectRepository;
import com.pwpo.user.UserAccount;
import com.pwpo.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectManager extends BaseService<Project, Long> {
    private final ItemMapper mapper;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final SearchService searchService;

    public ProjectManager(ProjectRepository repository, ItemMapper mapper, SearchService searchService,
                          List<? extends EntitySaveIntegrityValidation<Project>> validations, UserRepository userRepository) {
        super(repository, mapper, validations);
        this.projectRepository = repository;
        this.mapper = mapper;
        this.searchService = searchService;
        this.userRepository = userRepository;
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

//    private void validate(ProjectRequestDTO body) {
//        List<Project> projects = new ArrayList<>();
//        Iterable<Project> iterable = projectRepository.findAll();
//        iterable.forEach(projects::add);
//
//        if (projects.size() >= MAX_PROJECTS) {
//            throw new ValidationException("The project limit has been exceeded!");
//        }
//
//        if (projectRepository.findByName(body.getName()).isPresent()) {
//            throw new ValidationException("name", "Project with the given name already exists!");
//        }
//
//        if (projectRepository.findByShortForm(body.getShortForm()).isPresent()) {
//            throw new ValidationException("shortForm", "Project with the given short form already exists!");
//        }
//    }

    private boolean isTheSameProject(EditProjectRequestDTO<Long> body, Optional<Project> opt) {
        return !opt.get().getId().equals(body.getEntityId());
    }

    @Override
    protected void postSave(Project project) {

    }

    @Override
    protected void preSave(Project project) {
        project.setStatus(Status.NEW);
        project.setCreated(LocalDateTime.now());

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserAccount> byNick = userRepository.findByNick(principal.getUsername());

        project.setCreatedBy(byNick.get());
    }

    @Override
    protected Project mapDTO(ItemDTO body) {
        return mapper.mapToObj(body, Project.class);
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
