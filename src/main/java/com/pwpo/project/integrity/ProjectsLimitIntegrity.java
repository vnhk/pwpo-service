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
public class ProjectsLimitIntegrity implements EntitySaveIntegrityValidation<Project> {
    private final ProjectRepository projectRepository;

    public ProjectsLimitIntegrity(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ApiError> validate(Project entity) {
        List<ApiError> res = new ArrayList<>();

        if (projectRepository.findByName(entity.getName()).isPresent()) {
            res.add(new ApiError("name", ExceptionCode.FIELD_VALIDATION, "Project with the given name already exists!"));
        }

        if (projectRepository.findByShortForm(entity.getShortForm()).isPresent()) {
            res.add(new ApiError("shortForm", ExceptionCode.FIELD_VALIDATION, "Project with the given short form already exists!"));
        }

        return res;
    }
}
