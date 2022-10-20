package com.pwpo.project;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectHistoryRepository extends PagingAndSortingRepository<ProjectHistory, Long> {
    List<ProjectHistory> findAllByProjectId(Long projectId);
}
