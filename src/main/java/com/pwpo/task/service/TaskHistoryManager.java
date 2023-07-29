package com.pwpo.task.service;

import com.bervan.history.model.AbstractBaseHistoryEntity;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.QueryFormat;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.service.BaseHistoryService;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.task.TaskHistoryRepository;
import com.pwpo.task.dto.history.TaskHistoryDetailsResponseDTO;
import com.pwpo.task.model.Task;
import com.pwpo.task.model.TaskHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TaskHistoryManager extends BaseHistoryService<TaskHistory, Long> {
    private final TaskHistoryRepository taskHistoryRepository;

    public TaskHistoryManager(ItemMapper mapper, SearchService searchService, TaskHistoryRepository repository) {
        super(mapper, searchService);
        this.taskHistoryRepository = repository;
    }

    @Override
    protected void updateWithAttributesFromBaseEntity(AbstractBaseHistoryEntity<Long> history, APIResponse<? extends ItemDTO> apiResponse) {
        Task task = (Task) history.getEntity();
        TaskHistoryDetailsResponseDTO taskHistoryDetailsResponse = (TaskHistoryDetailsResponseDTO) apiResponse.getItems().get(0);
        taskHistoryDetailsResponse.setNumber(task.getNumber());
    }

    @Override
    protected Optional<? extends AbstractBaseHistoryEntity<Long>> getHistory(Long entityId, Long historyId) {
        return taskHistoryRepository.findByTaskIdAndId(entityId, historyId);
    }

    @Override
    protected String getQuery(Long id) {
        return String.format(QueryFormat.TASK_BY_ID, id);
    }
}
