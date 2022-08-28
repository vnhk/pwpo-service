package com.pwpo.project;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {
    private final ProjectManager projectManager;
    @GetMapping
    public ResponseEntity<ProjectResponse> getProjects() {
        return new ResponseEntity<>(projectManager.getProjects(), HttpStatus.OK);
    }
}
