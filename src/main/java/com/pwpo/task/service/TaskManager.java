package com.pwpo.task.service;

import com.pwpo.common.enums.Status;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.model.QueryFormat;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.search.model.SearchResponse;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.project.model.Project;
import com.pwpo.project.repository.ProjectRepository;
import com.pwpo.task.model.Task;
import com.pwpo.task.TaskRepository;
import com.pwpo.task.dto.TaskPrimaryResponseDTO;
import com.pwpo.task.dto.TaskRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskManager {
    public static final int MAX_TASKS_IN_PROJECT = 9998;
    private final ItemMapper mapper;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final SearchService searchService;

    public APIResponse getTasksByProjectId(String id, SearchQueryOption options, Class<? extends ItemDTO> dtoClass) {
        String query = String.format(QueryFormat.TASKS_BY_PROJECT_ID, Long.valueOf(id));
        SearchResponse search = searchService.search(query, options);

        return mapper.mapToAPIResponse(search, dtoClass);
    }


    public APIResponse getTaskById(String id, Class<? extends ItemDTO> dtoClass) {
        Optional<Task> task = taskRepository.findById(Long.parseLong(id));

        if (task.isPresent()) {
            return mapper.mapToAPIResponse(task.get(), dtoClass);
        } else {
            throw new RuntimeException("Could not find task!");
        }
    }

    public APIResponse create(TaskRequestDTO body) {
        setEstimation(body);

        Task task = mapper.mapToObj(body, Task.class);
        task.setProject(projectRepository.findById(task.getProject().getId()).get());
        setInitValues(task);
        Task saved = taskRepository.save(task);

        return mapper.mapToAPIResponse(saved, TaskPrimaryResponseDTO.class);
    }

    private void setEstimation(TaskRequestDTO body) {
        if(body.getEstimation() == null) {
            body.setEstimation(body.getEstimationInHours() * 60 + body.getEstimationInMinutes());
        }
    }

    private void setInitValues(Task task) {
        task.setStatus(Status.NEW);
        Project project = task.getProject();
        task.setCreated(LocalDateTime.now());
        task.setNumber(generateNumber(project));
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
