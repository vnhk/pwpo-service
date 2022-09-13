package com.pwpo.task;

import com.pwpo.TestUtils;
import com.pwpo.common.service.ItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class TaskManagerTest {
    private TaskManager taskManager;
    @Mock
    private TaskRepository taskRepository;
    private ItemMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ItemMapper(TestUtils.getObjectMapper());
        taskManager = new TaskManager(mapper, taskRepository);
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
}