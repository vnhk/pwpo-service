package com.pwpo.task.service;

import com.pwpo.common.enums.Status;
import com.pwpo.common.exception.NotFoundException;
import com.pwpo.common.exception.ValidationException;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.QueryFormat;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.model.edit.Editable;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.search.model.SearchResponse;
import com.pwpo.common.service.BaseService;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.common.validator.EntitySaveIntegrityValidation;
import com.pwpo.project.model.Project;
import com.pwpo.project.repository.ProjectRepository;
import com.pwpo.task.TaskRepository;
import com.pwpo.task.dto.TaskPrimaryResponseDTO;
import com.pwpo.task.dto.TaskRequestDTO;
import com.pwpo.task.model.EstimableDTO;
import com.pwpo.task.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class TaskManager extends BaseService<Task, Long> {
    public static final int MAX_TASKS_IN_PROJECT = 9998;
    private final ItemMapper mapper;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final SearchService searchService;

    public TaskManager(ItemMapper mapper, TaskRepository taskRepository, ProjectRepository projectRepository, SearchService searchService, List<? extends EntitySaveIntegrityValidation<Task>> validations) {
        super(taskRepository, mapper, validations);
        this.mapper = mapper;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.searchService = searchService;
    }

    public APIResponse getTasksByProjectId(String id, SearchQueryOption options, Class<? extends ItemDTO> dtoClass) {
        String query = String.format(QueryFormat.TASKS_BY_PROJECT_ID, Long.valueOf(id));
        SearchResponse search = searchService.search(query, options);

        return mapper.mapToAPIResponse(search, dtoClass);
    }


    @Override
    protected void validateEditRequest(Optional orig, Editable body) {
        if (orig.isEmpty()) {
            throw new ValidationException("Could not find task with given id!");
        }
    }

    @Override
    public APIResponse edit(Editable<Long> body) {
        setEstimation((EstimableDTO) body);
        return super.edit(body);
    }

    @Override
    protected void postSave(Task entity) {

    }

    @Override
    protected void preSave(Task task) {
        task.setProject(projectRepository.findById(task.getProject().getId()).get());
        task.setStatus(Status.NEW);
        Project project = task.getProject();
        task.setCreated(LocalDateTime.now());
        task.setNumber(generateNumber(project));
    }

    @Override
    protected Task mapDTO(ItemDTO body) {
        return mapper.mapToObj(body, Task.class);
    }

    public APIResponse getTaskById(String id, Class<? extends ItemDTO> dtoClass) {
        Optional<Task> task = taskRepository.findById(Long.parseLong(id));

        if (task.isPresent()) {
            return mapper.mapToAPIResponse(task.get(), dtoClass);
        } else {
            throw new NotFoundException("Could not find task!");
        }
    }

//    public APIResponse create(TaskRequestDTO body) {
//        setEstimation(body);
//
//        Task task = mapper.mapToObj(body, Task.class);
//        task.setProject(projectRepository.findById(task.getProject().getId()).get());
//        setInitValues(task);
//        Task saved = taskRepository.save(task);
//
//        return mapper.mapToAPIResponse(saved, TaskPrimaryResponseDTO.class);
//    }


    @Override
    public APIResponse<? extends ItemDTO> create(ItemDTO body, Class<? extends ItemDTO> responseType) {
        setEstimation((EstimableDTO) body);
        return super.create(body, responseType);
    }

    private void setEstimation(EstimableDTO body) {
        if (body.getEstimationValue() == null) {
            body.setEstimationValue(body.getEstimationInHoursValue() * 60 + body.getEstimationInMinutesValue());
        }
    }

    private String generateNumber(Project project) {
        String shortForm = project.getShortForm();
        List<Task> tasks = project.getTasks();

        if (tasks.size() < MAX_TASKS_IN_PROJECT) {
            while (true) {
                Random random = new Random();
                int number = random.nextInt(MAX_TASKS_IN_PROJECT) + 1;

                int missingZerosNumber = String.valueOf(MAX_TASKS_IN_PROJECT).length() - String.valueOf(number).length();

                String newNumber = shortForm + "0".repeat(Math.max(0, missingZerosNumber)) + number;

                if (tasks.stream().noneMatch(task -> task.getNumber().equals(newNumber))) {
                    return newNumber;
                }
            }
        }

        throw new RuntimeException("Could not generate task number. To many tasks added to the project!");
    }
}
