package com.pwpo.task;

import com.pwpo.common.service.PwpoBaseRepository;
import com.pwpo.task.model.TaskHistory;

import java.util.Optional;


public interface TaskHistoryRepository extends PwpoBaseRepository<TaskHistory, Long> {
    Optional<TaskHistory> findByTaskIdAndId(Long taskId, Long historyId);
}
