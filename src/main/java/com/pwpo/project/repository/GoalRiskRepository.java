package com.pwpo.project.repository;

import com.pwpo.common.service.BaseRepository;
import com.pwpo.project.model.GoalRisk;
import com.pwpo.task.timelog.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface GoalRiskRepository extends BaseRepository<GoalRisk, Long> {
}