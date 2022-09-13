package com.pwpo.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pwpo.common.model.APICollectionResponse;
import com.pwpo.task.dto.TaskPrimaryDTO;
import com.pwpo.task.dto.TaskSecondaryDTO;
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
    public ResponseEntity<APICollectionResponse> getTasksPrimaryByProjectId(@PathVariable String id) throws JsonProcessingException {
        return new ResponseEntity<>(taskManager.getTasksByProjectId(id, TaskPrimaryDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/secondary-attributes")
    public ResponseEntity<APICollectionResponse> getTasksSecondaryByProjectId(@PathVariable String id) throws JsonProcessingException {
        return new ResponseEntity<>(taskManager.getTasksByProjectId(id, TaskSecondaryDTO.class), HttpStatus.OK);
    }
}
