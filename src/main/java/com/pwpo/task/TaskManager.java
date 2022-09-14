package com.pwpo.task;

import com.pwpo.common.enums.Status;
import com.pwpo.common.model.APICollectionResponse;
import com.pwpo.common.model.ItemDTO;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.project.Project;
import com.pwpo.project.ProjectRepository;
import com.pwpo.task.dto.TaskPrimaryResponseDTO;
import com.pwpo.task.dto.TaskRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskManager {
    public static final int MAX_TASKS_IN_PROJECT = 9998;
    private final ItemMapper mapper;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public APICollectionResponse getTasksByProjectId(String id, Class<? extends ItemDTO> dtoClass) {
        List<Task> tasksByProjectId = taskRepository.findAllByProjectId(Long.valueOf(id));

        List<ItemDTO> collect = tasksByProjectId.stream().map(e -> mapper.mapToDTO(e, dtoClass))
                .collect(Collectors.toList());

        return new APICollectionResponse(collect, collect.size());
    }


    public ItemDTO getTaskById(String id, Class<? extends ItemDTO> dtoClass) {
        Optional<Task> task = taskRepository.findById(Long.parseLong(id));

        if (task.isPresent()) {
            return mapper.mapToDTO(task.get(), dtoClass);
        } else {
            throw new RuntimeException("Could not find task!");
        }
    }

    public APICollectionResponse getTasks(String assignee, Class<? extends ItemDTO> dtoClass) {
        return getTasksByProjectId("1", dtoClass);
    }

    public ItemDTO create(TaskRequestDTO body) {
        Task task = mapper.mapToObj(body, Task.class);
        task.setProject(projectRepository.findById(task.getProject().getId()).get());
        setInitValues(task);
        Task saved = taskRepository.save(task);

        return mapper.mapToDTO(saved, TaskPrimaryResponseDTO.class);
    }

    private void setInitValues(Task task) {
        task.setStatus(Status.NEW);
        Project project = task.getProject();
        task.setCreated(LocalDateTime.now());
        task.setModified(LocalDateTime.now());
        task.setDeleted(false);
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
