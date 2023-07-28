package com.pwpo.task;

import com.pwpo.common.service.PwpoBaseRepository;
import com.pwpo.task.model.Task;
import org.springframework.data.jpa.repository.Query;


public interface TaskRepository extends PwpoBaseRepository<Task, Long> {
    @Query("SELECT type, count(project.id) from Task WHERE project.id = ?1 group by type")
    Object[] getTaskTypeChartData(Long projectId);

    @Query("SELECT priority, count(id) from Task WHERE project.id = ?1 group by priority")
    Object[] getTaskPriorityChartData(Long projectId);

    @Query("SELECT sum(estimation) from Task WHERE project.id = ?1")
    Long getProjectSumEstimationTimeChartData(Long projectId);

    @Query("SELECT sum(log.loggedTimeInMinutes) from Task task, TimeLog log " +
            "WHERE task.project.id = ?1 AND log.task.id = task.id")
    Long getProjectSumLoggedTimeChartData(Long projectId);
}
