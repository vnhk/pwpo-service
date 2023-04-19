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
@RequestMapping(path = "/projects/{id}/goals-and-risks")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectGoalRiskController {
    private final ProjectManager projectManager;

    public ProjectGoalRiskController(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @GetMapping
    @ReadAccessProject
    public ResponseEntity<APIResponse> getAll(@PathVariable Long id, SortDirection sortDirection) {
        return new ResponseEntity<>(projectManager.getGoalsAndRisks(id, sortDirection), HttpStatus.OK);
    }

    @PostMapping
    @WriteAccessProject
    public ResponseEntity addOne(@PathVariable Long id, @Valid @RequestBody ProjectGoalRiskDTO projectGoalRiskDTO) {
        projectManager.addGoalRisk(id, projectGoalRiskDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @WriteAccessProject
    public ResponseEntity<APIResponse> editOne(@PathVariable Long id, @Valid @RequestBody ProjectGoalRiskDTO projectGoalRiskDTO) {
        projectManager.editGoalRisk(id, projectGoalRiskDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @WriteAccessProject
    public ResponseEntity<APIResponse> edit(@PathVariable Long id, @Valid @RequestBody ProjectGoalRiskDTO projectGoalRiskDTO) {
        projectManager.removeGoalRisk(id, projectGoalRiskDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
