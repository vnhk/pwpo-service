package com.pwpo.project.repository;

import com.pwpo.common.service.BaseRepository;
import com.pwpo.project.model.Project;

import java.util.Optional;


public interface ProjectRepository extends BaseRepository<Project, Long> {
    Optional<Project> findByName(String name);

    Optional<Project> findByShortForm(String shortForm);
}
