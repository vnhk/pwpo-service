package com.pwpo.task.controller;

import com.pwpo.common.controller.BaseEntityController;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.project.dto.EditProjectRequestDTO;
import com.pwpo.task.dto.EditTaskRequestDTO;
import com.pwpo.task.dto.TaskRequestDTO;
import com.pwpo.task.model.Task;
import com.pwpo.task.service.TaskManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController extends BaseEntityController<Task, Long> {
    private final TaskManager taskManager;

    public TaskController(TaskManager taskManager) {
        super(taskManager);
        this.taskManager = taskManager;
    }

    @GetMapping(path = "/task")
    public ResponseEntity<APIResponse> getTaskById(String id, String dto) throws ClassNotFoundException {
        return new ResponseEntity<>(taskManager.getTaskById(id, (Class<? extends ItemDTO>) Class.forName(dto)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIResponse> createTask(@Valid @RequestBody TaskRequestDTO body) {
        return new ResponseEntity<>(taskManager.create(body), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<APIResponse> edit(@Valid @RequestBody EditTaskRequestDTO<Long> body) {
        return super.edit(body);
    }
}
