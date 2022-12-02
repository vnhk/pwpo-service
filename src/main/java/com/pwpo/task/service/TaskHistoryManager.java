package com.pwpo.task.service;

import com.pwpo.common.model.QueryFormat;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.service.BaseHistoryService;
import com.pwpo.common.diff.DiffService;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.task.TaskHistoryRepository;
import com.pwpo.task.model.TaskHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TaskHistoryManager extends BaseHistoryService<TaskHistory, Long> {
    private final TaskHistoryRepository taskHistoryRepository;

    public TaskHistoryManager(ItemMapper mapper, SearchService searchService, DiffService diffService, TaskHistoryRepository repository) {
        super(mapper, searchService, diffService);
        this.taskHistoryRepository = repository;
    }

    @Override
    protected Optional<? extends BaseHistoryEntity> getHistory(Long entityId, Long historyId) {
        return taskHistoryRepository.findByTaskIdAndId(entityId, historyId);
    }

    @Override
    protected String getQuery(Long id) {
        return String.format(QueryFormat.TASK_BY_ID, id);
    }
}
