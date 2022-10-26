package com.pwpo.project;

import com.pwpo.common.service.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ProjectRepository extends BaseRepository<Project, Long> {
    Optional<Project> findByName(String name);

    Optional<Project> findByShortForm(String shortForm);
}
