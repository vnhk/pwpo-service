package com.pwpo.project;

import com.pwpo.common.service.BaseRepository;

import java.util.Optional;


public interface ProjectHistoryRepository extends BaseRepository<ProjectHistory, Long> {
    Optional<ProjectHistory> findByProjectIdAndId(Long projectId, Long historyId);
}
