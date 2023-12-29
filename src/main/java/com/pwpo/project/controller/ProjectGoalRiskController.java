package com.pwpo.project.controller;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.search.model.SortDirection;
import com.pwpo.project.dto.ProjectGoalRiskDTO;
import com.pwpo.project.service.ProjectManager;
import com.pwpo.security.methodsecurity.ReadAccessProject;
import com.pwpo.security.methodsecurity.WriteAccessProject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/projects/{projectId}/goals-and-risks")
@CrossOrigin(origins = "*")
public class ProjectGoalRiskController {
    private final ProjectManager projectManager;

    public ProjectGoalRiskController(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @GetMapping
    @ReadAccessProject
    public ResponseEntity<APIResponse> getAll(@PathVariable Long projectId, SortDirection sortDirection) {
        return new ResponseEntity<>(projectManager.getGoalsAndRisks(projectId, sortDirection), HttpStatus.OK);
    }

    @PostMapping
    @WriteAccessProject
    public ResponseEntity saveOne(@PathVariable Long projectId, @Valid @RequestBody ProjectGoalRiskDTO projectGoalRiskDTO) {
        projectManager.saveGoalRisk(projectId, projectGoalRiskDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @WriteAccessProject
    public ResponseEntity delete(@PathVariable Long projectId, @PathVariable Long id) {
        projectManager.removeGoalRisk(projectId, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
