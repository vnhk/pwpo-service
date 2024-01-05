package com.pwpo.task.service;

import com.pwpo.common.exception.ValidationException;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.common.validator.EntitySaveIntegrityValidation;
import com.pwpo.project.model.Project;
import com.pwpo.project.repository.ProjectRepository;
import com.pwpo.task.TaskRelationshipRepository;
import com.pwpo.task.TaskRepository;
import com.pwpo.task.enums.TaskRelationshipType;
import com.pwpo.task.enums.TaskType;
import com.pwpo.task.model.Task;
import com.pwpo.task.model.TaskRelationship;
import com.pwpo.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TaskManagerTest {

    private TaskManager taskManager;
    @Mock
    private TaskRelationshipRepository taskRelationshipRepository;
    @Mock
    private ItemMapper mapper;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private SearchService searchService;
    private final List<? extends EntitySaveIntegrityValidation<Task>> validations = new ArrayList<>();
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void before() {
        taskManager = new TaskManager(mapper, taskRepository, projectRepository, searchService,
                validations, userRepository, taskRelationshipRepository);
    }

    @Test
    void appendSubTaskValidations_differentProjects() {
        Task t0001 = Task.builder().number("T0001").build();
        t0001.setId(1L);
        Task t0002 = Task.builder().number("T0002").build();
        t0002.setId(2L);

        when(taskRepository.findByNumber("T0001"))
                .thenReturn(Optional.of(t0001));
        when(taskRepository.findByNumber("T0002"))
                .thenReturn(Optional.of(t0002));

        //different projects
        Project project1 = new Project();
        project1.setId(100L);
        Project project2 = new Project();
        project2.setId(200L);
        t0001.setProject(project1);
        t0002.setProject(project2);
        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("Tasks are from different projects!");
    }

    @Test
    void appendSubTaskValidations_theSameTask() {
        Task t0001 = Task.builder().number("T0001").build();
        t0001.setId(1L);

        when(taskRepository.findByNumber("T0001"))
                .thenReturn(Optional.of(t0001));

        Project project1 = new Project();
        project1.setId(100L);
        t0001.setProject(project1);
        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0001", "T0001", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("Task cannot be connected to itself!");

    }

    @Test
    void appendSubTaskValidations_relationship_already_exists() {
        Task t0001 = Task.builder().number("T0001").build();
        t0001.setId(1L);
        Task t0002 = Task.builder().number("T0002").build();
        t0002.setId(2L);

        when(taskRepository.findByNumber("T0001"))
                .thenReturn(Optional.of(t0001));
        when(taskRepository.findByNumber("T0002"))
                .thenReturn(Optional.of(t0002));

        Project project1 = new Project();
        project1.setId(100L);
        t0001.setProject(project1);
        t0002.setProject(project1);

        //the relationship already exists
        TaskRelationship t = new TaskRelationship();
        t.setType(TaskRelationshipType.CHILD_IS_PART_OF);
        t.setParent(t0001);
        t.setChild(t0002);
        t0001.setParentRelationships(Arrays.asList(t));
        t0002.setChildRelationships(Arrays.asList(t));
        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("The structure already exists!");
    }

    @Test
    void appendSubTaskValidations_task_cannot_be_parent_and_child_of_the_same_task() {
        Task t0001 = Task.builder().number("T0001").build();
        t0001.setId(1L);
        Task t0002 = Task.builder().number("T0002").build();
        t0002.setId(2L);

        when(taskRepository.findByNumber("T0001"))
                .thenReturn(Optional.of(t0001));
        when(taskRepository.findByNumber("T0002"))
                .thenReturn(Optional.of(t0002));

        Project project1 = new Project();
        project1.setId(100L);
        t0001.setProject(project1);
        t0002.setProject(project1);

        //the relationship t1 parent, t2 child exists
        TaskRelationship t = new TaskRelationship();
        t.setType(TaskRelationshipType.CHILD_IS_PART_OF);
        t.setParent(t0001);
        t.setChild(t0002);
        t0001.setParentRelationships(Arrays.asList(t));
        t0002.setChildRelationships(Arrays.asList(t));

        //creating relationship where t0001 is child of t0002
        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0002", "T0001", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("Task cannot be parent and child of the same task!");
    }

    @Test
    void appendSubTaskValidations_task_type_validations() {
        Task t0001 = Task.builder().number("T0001").build();
        t0001.setId(1L);
        Task t0002 = Task.builder().number("T0002").build();
        t0002.setId(2L);

        when(taskRepository.findByNumber("T0001"))
                .thenReturn(Optional.of(t0001));
        when(taskRepository.findByNumber("T0002"))
                .thenReturn(Optional.of(t0002));

        Project project1 = new Project();
        project1.setId(100L);
        t0001.setProject(project1);
        t0002.setProject(project1);

        t0001.setType(TaskType.TASK);
        t0002.setType(TaskType.STORY);

        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("Story cannot be subtask of task!");

        t0002.setType(TaskType.FEATURE);
        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("Feature cannot be subtask of task!");

        t0002.setType(TaskType.OBJECTIVE);
        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("Objective cannot be subtask of task!");

        t0001.setType(TaskType.BUG);
        t0002.setType(TaskType.STORY);

        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("Story cannot be subtask of bug!");

        t0002.setType(TaskType.FEATURE);
        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("Feature cannot be subtask of bug!");

        t0002.setType(TaskType.OBJECTIVE);
        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("Objective cannot be subtask of bug!");

        t0001.setType(TaskType.STORY);
        t0002.setType(TaskType.FEATURE);

        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("Feature cannot be subtask of story!");

        t0002.setType(TaskType.OBJECTIVE);
        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("Objective cannot be subtask of story!");

        t0001.setType(TaskType.FEATURE);
        t0002.setType(TaskType.OBJECTIVE);
        assertThatExceptionOfType(ValidationException.class).isThrownBy(() ->
                        taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF))
                .withMessage("Objective cannot be subtask of feature!");

        t0001.setType(TaskType.OBJECTIVE);
        t0002.setType(TaskType.OBJECTIVE);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.OBJECTIVE);
        t0002.setType(TaskType.FEATURE);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.OBJECTIVE);
        t0002.setType(TaskType.STORY);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.OBJECTIVE);
        t0002.setType(TaskType.TASK);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.OBJECTIVE);
        t0002.setType(TaskType.BUG);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.FEATURE);
        t0002.setType(TaskType.FEATURE);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.FEATURE);
        t0002.setType(TaskType.STORY);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.FEATURE);
        t0002.setType(TaskType.TASK);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.FEATURE);
        t0002.setType(TaskType.BUG);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.STORY);
        t0002.setType(TaskType.STORY);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.STORY);
        t0002.setType(TaskType.TASK);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.STORY);
        t0002.setType(TaskType.BUG);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.TASK);
        t0002.setType(TaskType.TASK);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.TASK);
        t0002.setType(TaskType.BUG);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.BUG);
        t0002.setType(TaskType.TASK);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));

        t0001.setType(TaskType.BUG);
        t0002.setType(TaskType.BUG);
        assertThatNoException().isThrownBy(() -> taskManager.createRelationship("T0001", "T0002", TaskRelationshipType.CHILD_IS_PART_OF));
    }
}