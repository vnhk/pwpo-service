package com.pwpo.task;

import com.pwpo.common.service.BaseRepository;
import com.pwpo.project.model.ProjectHistory;
import com.pwpo.task.model.TaskHistory;

import java.util.Optional;


public interface TaskHistoryRepository extends BaseRepository<TaskHistory, Long> {
    Optional<TaskHistory> findByTaskIdAndId(Long taskId, Long historyId);
}
