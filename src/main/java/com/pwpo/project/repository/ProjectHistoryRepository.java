package com.pwpo.project.repository;

import com.pwpo.common.service.BaseRepository;
import com.pwpo.project.model.ProjectHistory;

import java.util.Optional;


public interface ProjectHistoryRepository extends BaseRepository<ProjectHistory, Long> {
    Optional<ProjectHistory> findByProjectIdAndId(Long projectId, Long historyId);
}
