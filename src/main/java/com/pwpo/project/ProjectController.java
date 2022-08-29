package com.pwpo.project;

import com.pwpo.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {
    private final ProjectManager projectManager;

    @GetMapping
    public ResponseEntity<APIResponse> getProjects() {
        return new ResponseEntity<>(projectManager.getProjects(), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable String id) {
        return new ResponseEntity<>(projectManager.getProjectById(id), HttpStatus.OK);
    }
}
