package com.pwpo.project.repository;

import com.pwpo.common.service.PwpoBaseRepository;
import com.pwpo.project.model.ProjectHistory;

import java.util.Optional;


public interface ProjectHistoryRepository extends PwpoBaseRepository<ProjectHistory, Long> {
    Optional<ProjectHistory> findByProjectIdAndId(Long projectId, Long historyId);
}
