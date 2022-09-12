package com.pwpo.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwpo.APICollectionResponse;
import com.pwpo.ItemDTO;
import com.pwpo.common.enums.Priority;
import com.pwpo.common.enums.Status;
import com.pwpo.task.enums.TaskType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskManager {
    private final ObjectMapper objectMapper;
    private final TaskRepository taskRepository;

    public APICollectionResponse getTasksByProjectId(String id, Class<? extends ItemDTO> dtoClass) {
        List<Task> mockProjectsDTO = getMockTasksDTO();

        List<ItemDTO> collect = mockProjectsDTO.stream().map(e -> {
            try {
                return mapToDTO(e, dtoClass);
            } catch (JsonProcessingException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        return new APICollectionResponse(collect, collect.size());
    }


    private ItemDTO mapToDTO(Task task, Class<? extends ItemDTO> dtoClass) throws JsonProcessingException {
        String projectAsString = objectMapper.writeValueAsString(task);
        return objectMapper.readValue(projectAsString, dtoClass);
    }

    private List<Task> getMockTasksDTO() {
        List<Task> mockTasks = new ArrayList<>();
        int amount = 40;
        for (int i = 0; i < amount; i++) {
            mockTasks.add(getTaskMock(i));
        }
        return mockTasks;
    }

    private Task getTaskMock(int i) {
        return Task.builder()
                .id((long) i)
                .number("PDUT00" + i)
                .description("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?" + i)
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
//                .createdBy("Max Wasserman" + i)
                .status(Status.OPEN)
                .assignee("Joe Doe" + i)
                .dueDate(LocalDateTime.now())
                .priority(Priority.CRITICAL)
                .type(TaskType.FEATURE)
                .summary("But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful" + i)
                .build();
    }

    public ItemDTO getTaskById(String id, Class<? extends ItemDTO> dtoClass) {
        return null;
    }

    public APICollectionResponse getTasks(String assignee, Class<? extends ItemDTO> dtoClass) {
        return getTasksByProjectId(null, dtoClass);
    }
}
