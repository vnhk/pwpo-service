package com.pwpo.project.controller;

import com.pwpo.common.ChartData;
import com.pwpo.common.controller.BaseEntityController;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.project.dto.EditProjectRequestDTO;
import com.pwpo.project.dto.ProjectPrimaryResponseDTO;
import com.pwpo.project.dto.ProjectRequestDTO;
import com.pwpo.project.model.Project;
import com.pwpo.project.service.ProjectManager;
import com.pwpo.project.service.VisualizationProjectManager;
import com.pwpo.user.UserManager;
import com.pwpo.user.dto.UserProjectDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController extends BaseEntityController<Project, Long> {
    private final ProjectManager projectManager;
    private final UserManager userManager;
    private final VisualizationProjectManager visualizationProjectManager;

    public ProjectController(ProjectManager projectManager, UserManager userManager, VisualizationProjectManager visualizationProjectManager) {
        super(projectManager);
        this.projectManager = projectManager;
        this.userManager = userManager;
        this.visualizationProjectManager = visualizationProjectManager;
    }

    @GetMapping
    public ResponseEntity<APIResponse> getProjectsPrimary(SearchQueryOption options) {
        return new ResponseEntity<>(projectManager.getProjects(options, ProjectPrimaryResponseDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}/exists")
    public ResponseEntity<Boolean> exist(@PathVariable Long id) {
        return super.exist(id);
    }

    @GetMapping(path = "/project")
    public ResponseEntity<APIResponse> getProject(Long id, String dto) throws ClassNotFoundException {
        return new ResponseEntity<>(projectManager.getProjectById(id, (Class<? extends ItemDTO>) Class.forName(dto)), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}/users")
    public ResponseEntity<APIResponse> getUsersAddedToTheProject(@PathVariable Long id, SearchQueryOption options) {
        return new ResponseEntity<>(userManager.getUsersAddedToProject(id, options), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}/visualization/task-types")
    public ResponseEntity<ChartData[]> getTaskTypesVisualization(@PathVariable Long id) {
        return new ResponseEntity<>(visualizationProjectManager.getTaskTypes(id), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}/visualization/project-roles")
    public ResponseEntity<ChartData[]> getRolesChartData(@PathVariable Long id) {
        return new ResponseEntity<>(visualizationProjectManager.getRolesChartData(id), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}/visualization/sum-time")
    public ResponseEntity<ChartData[]> getProjectSumTimeChartData(@PathVariable Long id) {
        return new ResponseEntity<>(visualizationProjectManager.getProjectSumTimeChartData(id), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}/visualization/task-priorities")
    public ResponseEntity<ChartData[]> getTaskPrioritiesVisualization(@PathVariable Long id) {
        return new ResponseEntity<>(visualizationProjectManager.getTaskPriorities(id), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}/users-not-added")
    public ResponseEntity<APIResponse> getUsersNotAddedToTheProject(@PathVariable Long id) {
        return new ResponseEntity<>(userManager.getUsersNotAddedToProject(id), HttpStatus.OK);
    }

    @PostMapping(path = "/project/{id}/users")
    public ResponseEntity addUserToTheProject(@PathVariable Long id, @Valid @RequestBody UserProjectDTO userProject) {
        userManager.addUserToTheProject(id, userProject.getUser(), userProject.getProjectRole());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIResponse> createProject(@Valid @RequestBody ProjectRequestDTO body) {
        return new ResponseEntity<>(projectManager.create(body, ProjectPrimaryResponseDTO.class), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<APIResponse> edit(@Valid @RequestBody EditProjectRequestDTO<Long> body) {
        return super.edit(body);
    }
}
