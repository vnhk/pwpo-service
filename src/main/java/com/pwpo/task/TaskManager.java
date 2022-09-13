package com.pwpo.task;

import com.pwpo.common.EnumDTO;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.common.model.APICollectionResponse;
import com.pwpo.common.model.ItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskManager {
    private final ItemMapper mapper;
    private final TaskRepository taskRepository;

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
}
