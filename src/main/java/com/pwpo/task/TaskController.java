package com.pwpo.task;

import com.pwpo.common.model.APICollectionResponse;
import com.pwpo.common.model.ItemDTO;
import com.pwpo.task.dto.TaskPrimaryResponseDTO;
import com.pwpo.task.dto.TaskRequestDTO;
import com.pwpo.task.dto.TaskSecondaryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
    private final TaskManager taskManager;


    @GetMapping(path = "/task/{id}/primary-attributes")
    public ResponseEntity<? extends ItemDTO> getTaskPrimaryById(@PathVariable String id) {
        return new ResponseEntity<>(taskManager.getTaskById(id, TaskPrimaryResponseDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/task/{id}/secondary-attributes")
    public ResponseEntity<? extends ItemDTO> getTaskSecondaryById(@PathVariable String id) {
        return new ResponseEntity<>(taskManager.getTaskById(id, TaskSecondaryResponseDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<APICollectionResponse> getTasks(@RequestParam(required = false) String assignee,
                                                          @RequestParam(required = false) String createdBy) {
        return new ResponseEntity<>(taskManager.getTasks(assignee, TaskPrimaryResponseDTO.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<? extends ItemDTO> createTask(@Validated @RequestBody TaskRequestDTO body) {
        System.out.println(body);
        return new ResponseEntity<>(taskManager.create(body), HttpStatus.OK);
    }
}
