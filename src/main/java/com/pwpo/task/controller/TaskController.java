package com.pwpo.task.controller;

import com.pwpo.task.dto.TaskRequestDTO;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.task.service.TaskManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
    private final TaskManager taskManager;

    @GetMapping(path = "/task")
    public ResponseEntity<APIResponse> getTaskById(String id, String dto) throws ClassNotFoundException {
        return new ResponseEntity<>(taskManager.getTaskById(id, (Class<? extends ItemDTO>) Class.forName(dto)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIResponse> createTask(@Valid @RequestBody TaskRequestDTO body) {
        return new ResponseEntity<>(taskManager.create(body), HttpStatus.OK);
    }
}
