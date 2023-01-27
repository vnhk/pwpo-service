package com.pwpo.project.integrity;

import com.pwpo.common.exception.ApiError;
import com.pwpo.common.exception.ExceptionCode;
import com.pwpo.common.validator.EntitySaveIntegrityValidation;
import com.pwpo.project.model.Project;
import com.pwpo.project.repository.ProjectRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UniqueProjectIntegrity implements EntitySaveIntegrityValidation<Project> {
    public static final int MAX_PROJECTS = 100;
    private final ProjectRepository projectRepository;

    public UniqueProjectIntegrity(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ApiError> validate(Project entity) {
        List<ApiError> res = new ArrayList<>();

        // TODO: 06.10.2022 replace with count(*)
        if (projectRepository.findAll().size() >= MAX_PROJECTS) {
            res.add(new ApiError(ExceptionCode.GENERAL_VALIDATION, "The project limit has been exceeded!"));
        }

        return res;
    }
}
