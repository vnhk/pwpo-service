package com.pwpo.project;

import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.project.dto.ProjectPrimaryResponseDTO;
import com.pwpo.project.dto.ProjectRequestDTO;
import com.pwpo.user.UserManager;
import com.pwpo.user.dto.UserDTO;
import com.pwpo.user.dto.UserProjectDTO;
import com.pwpo.user.model.APIResponse;
import com.pwpo.user.model.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {
    private final ProjectManager projectManager;
    private final UserManager userManager;

    @GetMapping
    public ResponseEntity<APIResponse> getProjectsPrimary(SearchQueryOption options) {
        return new ResponseEntity<>(projectManager.getProjects(options, ProjectPrimaryResponseDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/project")
    public ResponseEntity<APIResponse> getProject(Long id, String dto) throws ClassNotFoundException {
        return new ResponseEntity<>(projectManager.getProjectById(id, (Class<? extends ItemDTO>) Class.forName(dto)), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}/users")
    public ResponseEntity<APIResponse> getUsersAddedToTheProject(@PathVariable Long id) {
        return new ResponseEntity<>(userManager.getUsersAddedToProject(id, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}/users-not-added")
    public ResponseEntity<APIResponse> getUsersNotAddedToTheProject(@PathVariable Long id) {
        return new ResponseEntity<>(userManager.getUsersNotAddedToProject(id, UserDTO.class), HttpStatus.OK);
    }

    @PostMapping(path = "/project/{id}/users")
    public ResponseEntity addUserToTheProject(@PathVariable Long id, @Valid @RequestBody UserProjectDTO userProject) {
        userManager.addUserToTheProject(id, userProject.getUser(), userProject.getProjectRole());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIResponse> createProject(@Valid @RequestBody ProjectRequestDTO body) {
        return new ResponseEntity<>(projectManager.create(body), HttpStatus.OK);
    }
}
