package com.pwpo.task;

import com.pwpo.common.model.APICollectionResponse;
import com.pwpo.common.model.ItemDTO;
import com.pwpo.task.dto.TaskPrimaryDTO;
import com.pwpo.task.dto.TaskSecondaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
    private final TaskManager taskManager;


    @GetMapping(path = "/task/{id}/primary-attributes")
    public ResponseEntity<? extends ItemDTO> getTaskPrimaryById(@PathVariable String id) {
        return new ResponseEntity<>(taskManager.getTaskById(id, TaskPrimaryDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/task/{id}/secondary-attributes")
    public ResponseEntity<? extends ItemDTO> getTaskSecondaryById(@PathVariable String id) {
        return new ResponseEntity<>(taskManager.getTaskById(id, TaskSecondaryDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<APICollectionResponse> getTasks(@RequestParam(required = false) String assignee,
                                                          @RequestParam(required = false) String createdBy) {
        return new ResponseEntity<>(taskManager.getTasks(assignee, TaskPrimaryDTO.class), HttpStatus.OK);
    }
}
