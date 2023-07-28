package com.pwpo.project.repository;

import com.pwpo.common.service.PwpoBaseRepository;
import com.pwpo.project.model.Project;

import java.util.Optional;


public interface ProjectRepository extends PwpoBaseRepository<Project, Long> {
    Optional<Project> findByName(String name);

    Optional<Project> findByShortForm(String shortForm);
}
