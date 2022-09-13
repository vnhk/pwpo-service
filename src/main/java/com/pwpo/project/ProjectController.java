package com.pwpo.project;

import com.pwpo.common.model.APICollectionResponse;
import com.pwpo.common.model.ItemDTO;
import com.pwpo.project.dto.ProjectPrimaryDTO;
import com.pwpo.project.dto.ProjectSecondaryDTO;
import com.pwpo.user.UserManager;
import com.pwpo.user.dto.UserDTO;
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
    private final UserManager userManager;

    @GetMapping
    public ResponseEntity<APICollectionResponse> getProjectsPrimary() {
        return new ResponseEntity<>(projectManager.getProjects(ProjectPrimaryDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}/primary-attributes")
    public ResponseEntity<? extends ItemDTO> getProjectPrimary(@PathVariable String id) {
        return new ResponseEntity<>(projectManager.getProjectById(id, ProjectPrimaryDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}/secondary-attributes")
    public ResponseEntity<? extends ItemDTO> getProjectSecondary(@PathVariable String id) {
        return new ResponseEntity<>(projectManager.getProjectById(id, ProjectSecondaryDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/project/{id}/users")
    public ResponseEntity<APICollectionResponse> getUsersAddedToTheProject(@PathVariable String id) {
        return new ResponseEntity<>(userManager.getUsersAddedToProject(id, UserDTO.class), HttpStatus.OK);
    }
}
