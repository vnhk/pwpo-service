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
import com.pwpo.task.TaskRelationshipRepository;
import com.pwpo.task.TaskRepository;
import com.pwpo.task.dto.EditTaskRequestDTO;
import com.pwpo.task.dto.TaskPrimaryResponseDTO;
import com.pwpo.task.dto.TaskStructureResponseDTO;
import com.pwpo.task.enums.TaskRelationshipType;
import com.pwpo.task.model.EstimableDTO;
import com.pwpo.task.model.Task;
import com.pwpo.task.model.TaskRelationship;
import com.pwpo.user.UserAccount;
import com.pwpo.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class TaskManager extends BaseService<Task, Long> {
    public static final int MAX_TASKS_IN_PROJECT = 9998;
    private final ItemMapper mapper;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final SearchService searchService;
    private final UserRepository userRepository;
    private final TaskRelationshipRepository taskRelationshipRepository;

    public TaskManager(ItemMapper mapper, TaskRepository taskRepository, ProjectRepository projectRepository,
                       SearchService searchService, List<? extends EntitySaveIntegrityValidation<Task>> validations,
                       UserRepository userRepository, TaskRelationshipRepository taskRelationshipRepository) {
        super(taskRepository, mapper, validations);
        this.mapper = mapper;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.searchService = searchService;
        this.userRepository = userRepository;
        this.taskRelationshipRepository = taskRelationshipRepository;
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


        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserAccount> byNick = userRepository.findByNick(principal.getUsername());

        task.setCreatedBy(byNick.get());
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

    public void assignTask(Long id) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAccount userAccount = userRepository.findByNick(principal.getUsername()).get();

        Task task = getTask(id);
        task.setAssignee(userAccount);

        taskRepository.edit(adaptTaskToEditable(task));
    }

    private EditTaskRequestDTO<Long> adaptTaskToEditable(Task task) {
        EditTaskRequestDTO editTaskRequestDTO = new EditTaskRequestDTO();
        editTaskRequestDTO.setId(task.getId());
        editTaskRequestDTO.setAssignee(task.getAssignee() == null ? null : task.getAssignee().getId());
        editTaskRequestDTO.setEstimation(task.getEstimation());
        editTaskRequestDTO.setStatus(task.getStatus());
        editTaskRequestDTO.setDescription(task.getDescription());
        editTaskRequestDTO.setDueDate(task.getDueDate());
        editTaskRequestDTO.setOwner(task.getOwner().getId());
        editTaskRequestDTO.setPriority(task.getPriority());
        editTaskRequestDTO.setSummary(task.getSummary());
        editTaskRequestDTO.setType(task.getType());


        return editTaskRequestDTO;
    }

    public void changeStatus(EditTaskRequestDTO<Long> editTaskRequestDTO) {
        Task task = getTask(editTaskRequestDTO.getId());

        if (editTaskRequestDTO.getStatus().equals(task.getStatus())) {
            return;
        }

        task.setStatus(editTaskRequestDTO.getStatus());

        taskRepository.edit(adaptTaskToEditable(task));
    }

    public void appendSubTask(Long taskId, Long subTaskId, TaskRelationshipType type) {
        if (Objects.equals(taskId, subTaskId)) {
            throw new ValidationException("Task cannot be subtask: Task = Sub Task");
        }
        Task task = getTask(taskId);
        Task subTask = getTask(subTaskId);
        Optional<TaskRelationship> existingRelationShip = task.getRelationships().stream()
                .filter(e -> e.getParent().getId().equals(task.getId()))
                .filter(e -> e.getType() == type)
                .filter(e -> e.getChild().getId().equals(subTask.getId())).findAny();
        if (existingRelationShip.isPresent()) {
            throw new ValidationException("Task cannot be subtask: Task = Sub Task");
        }

        TaskRelationship taskRelationship = new TaskRelationship();
        taskRelationship.setParent(task);
        taskRelationship.setChild(subTask);
        taskRelationship.setType(type);
        taskRelationship = taskRelationshipRepository.save(taskRelationship);
        task.getRelationships().add(taskRelationship);
        subTask.getRelationships().add(taskRelationship);

        taskRepository.save(task);
        taskRepository.save(subTask);
    }

    private Task getTask(Long id) {
        Optional<Task> subTaskOptional = taskRepository.findById(id);
        if (subTaskOptional.isEmpty()) {
            throw new NotFoundException("Could not find task!");
        }

        return subTaskOptional.get();
    }

    public APIResponse getTaskOneLevelStructure(Long id) {
        List<TaskStructureResponseDTO> response = new ArrayList<>();
        Task task = getTask(id);
        List<TaskRelationship> children = taskRelationshipRepository.findAllByParentAndType(task, TaskRelationshipType.CHILD_IS_PART_OF);
        List<TaskRelationship> parents = taskRelationshipRepository.findAllByChildAndType(task, TaskRelationshipType.CHILD_IS_PART_OF);
        ItemDTO paramTask = mapper.mapToDTO(task, TaskPrimaryResponseDTO.class);

        if (parents.size() > 1) {
            throw new ValidationException("Task structure is incorrect! More than one parent found!");
        } else if (parents.size() == 1) {
            ItemDTO itemDTO = mapper.mapToDTO(parents.get(0).getParent(), TaskPrimaryResponseDTO.class);
            TaskStructureResponseDTO r = TaskStructureResponseDTO.builder()
                    .type(TaskRelationshipType.CHILD_IS_PART_OF)
                    .parent((TaskPrimaryResponseDTO) itemDTO)
                    .child((TaskPrimaryResponseDTO) paramTask).build();
            response.add(r);
        }

        for (TaskRelationship child : children) {
            ItemDTO itemDTO = mapper.mapToDTO(child.getChild(), TaskPrimaryResponseDTO.class);
            TaskStructureResponseDTO r = TaskStructureResponseDTO.builder()
                    .type(child.getType())
                    .parent((TaskPrimaryResponseDTO) paramTask)
                    .child((TaskPrimaryResponseDTO) itemDTO).build();
            response.add(r);
        }
        return new APIResponse(response, response.size(), 0, response.size());
    }
}
