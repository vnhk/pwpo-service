package com.pwpo.task;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/projects/project/{id}/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectTaskController {
    private final TaskManager taskManager;

    @GetMapping(path = "/primary-attributes")
    public ResponseEntity<APIResponse> getTasksPrimaryByProjectId(@PathVariable String id) {
        throw new RuntimeException("Deprecated! Use /projects/project/{id}/tasks/ with dtoClass!");
    }

    @GetMapping(path = "/secondary-attributes")
    public ResponseEntity<APIResponse> getTasksSecondaryByProjectId(@PathVariable String id) {
        throw new RuntimeException("Deprecated! Use /projects/project/{id}/tasks/ with dtoClass!");
    }

    @GetMapping(path = "/")
    public ResponseEntity<APIResponse> getTasksByProjectId(@PathVariable String id, SearchQueryOption options, String dto) throws ClassNotFoundException {
        return new ResponseEntity<>(taskManager.getTasksByProjectId(id, options, (Class<? extends ItemDTO>) Class.forName(dto)), HttpStatus.OK);
    }
}
