package com.pwpo.task.controller;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.security.methodsecurity.ReadAccessProject;
import com.pwpo.task.service.TaskManager;
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

    @GetMapping
    @ReadAccessProject
    public ResponseEntity<APIResponse> getTasksByProjectId(@PathVariable String id, SearchQueryOption options, String dto) throws ClassNotFoundException {
        return new ResponseEntity<>(taskManager.getTasksByProjectId(id, options, (Class<? extends ItemDTO>) Class.forName(dto)), HttpStatus.OK);
    }
}
