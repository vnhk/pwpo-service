package com.pwpo.task;

import com.pwpo.TestUtils;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.project.Project;
import com.pwpo.project.ProjectRepository;
import com.pwpo.task.dto.TaskRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TaskManagerTest {
    private TaskManager taskManager;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private SearchService searchService;
    private ItemMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ItemMapper(TestUtils.getObjectMapper());
        taskManager = new TaskManager(mapper, taskRepository, projectRepository, searchService);
    }

    @Test
    void getTasksByProjectId() {
    }

    @Test
    void getTaskById() {
    }

    @Test
    void getTasks() {
    }

    @Test
    void generateNumber() {
        TaskRequestDTO body = TaskRequestDTO.builder()
                .project(1L)
                .build();

        Project project = new Project();
        project.setId(1L);
        project.setShortForm("TST");
        project.setTasks(new ArrayList<>());

        Optional<Project> projectOptional = Optional.of(project);
        when(projectRepository.findById(1L))
                .thenReturn(projectOptional);

        APIResponse apiResponse = taskManager.create(body);

        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void generateNumberWhenToManyTasks() {
        TaskRequestDTO body = TaskRequestDTO.builder()
                .project(1L)
                .build();

        Project project = new Project();
        project.setId(1L);
        project.setShortForm("TST");
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < TaskManager.MAX_TASKS_IN_PROJECT + 10; i++) {
            tasks.add(new Task());
        }
        project.setTasks(tasks);
        Optional<Project> projectOptional = Optional.of(project);

        when(projectRepository.findById(1L))
                .thenReturn(projectOptional);

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> taskManager.create(body))
                .withMessage("Could not generate task number. To many tasks added to the project!");
    }
}