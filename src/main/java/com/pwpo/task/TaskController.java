package com.pwpo.task;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.ItemDTO;
import com.pwpo.task.dto.TaskPrimaryResponseDTO;
import com.pwpo.task.dto.TaskRequestDTO;
import com.pwpo.task.dto.TaskSecondaryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
    private final TaskManager taskManager;


    @GetMapping(path = "/task/{id}/primary-attributes")
    public ResponseEntity<APIResponse> getTaskPrimaryById(@PathVariable String id) {
        throw new RuntimeException("Deprecated! Use /tasks/task/{id}/ with dtoClass!");
    }

    @GetMapping(path = "/task/{id}/secondary-attributes")
    public ResponseEntity<APIResponse> getTaskSecondaryById(@PathVariable String id) {
        throw new RuntimeException("Deprecated! Use /tasks/task/{id} with dtoClass!");
    }

    @GetMapping(path = "/task/{id}")
    public ResponseEntity<APIResponse> getTaskById(@PathVariable String id, String dto) throws ClassNotFoundException {
        return new ResponseEntity<>(taskManager.getTaskById(id, (Class<? extends ItemDTO>) Class.forName(dto)), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<APIResponse> getTasks(@RequestParam(required = false) String assignee,
                                                @RequestParam(required = false) String createdBy) {
        throw new RuntimeException("Deprecated! Use search from SearchController!");
    }

    @PostMapping
    public ResponseEntity<APIResponse> createTask(@Validated @RequestBody TaskRequestDTO body) {
        System.out.println(body);
        return new ResponseEntity<>(taskManager.create(body), HttpStatus.OK);
    }
}
