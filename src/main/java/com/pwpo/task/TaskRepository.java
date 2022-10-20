package com.pwpo.task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
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
