package com.pwpo.task.controller;

import com.pwpo.common.controller.BaseEntityController;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.task.dto.EditTaskRequestDTO;
import com.pwpo.task.dto.TaskPrimaryResponseDTO;
import com.pwpo.task.dto.TaskRequestDTO;
import com.pwpo.task.enums.TaskRelationshipType;
import com.pwpo.task.model.Task;
import com.pwpo.task.service.TaskManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/tasks")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}
        , exposedHeaders = "*")
public class TaskController extends BaseEntityController<Task, Long> {
    private final TaskManager taskManager;

    public TaskController(TaskManager taskManager) {
        super(taskManager);
        this.taskManager = taskManager;
    }

    @GetMapping(path = "/task")
    @PreAuthorize("(@permissionEvaluator.activatedAndHasRole('USER') && @permissionEvaluator.hasAccessToProjectTask(#id)) or @permissionEvaluator.activatedAndHasRole('MANAGER')")
    public ResponseEntity<APIResponse> getTaskById(String id, String dto) throws ClassNotFoundException {
        return new ResponseEntity<>(taskManager.getTaskById(id, (Class<? extends ItemDTO>) Class.forName(dto)), HttpStatus.OK);
    }

    @GetMapping(path = "/task/{id}/children")
    @PreAuthorize("(@permissionEvaluator.activatedAndHasRole('USER') && @permissionEvaluator.hasAccessToProjectTask(#id)) or @permissionEvaluator.activatedAndHasRole('MANAGER')")
    public ResponseEntity<APIResponse> getTaskChildrenStructure(@PathVariable Long id) {
        return new ResponseEntity<>(taskManager.getTaskChildrenStructure(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("(@permissionEvaluator.activatedAndHasRole('USER') && @permissionEvaluator.hasAccessToProject(#body.project)) or @permissionEvaluator.activatedAndHasRole('MANAGER')")
    public ResponseEntity<APIResponse> createTask(@Valid @RequestBody TaskRequestDTO body) {
        return new ResponseEntity<>(taskManager.create(body, TaskPrimaryResponseDTO.class), HttpStatus.OK);
    }

    @PostMapping("/task/{taskId}/append-subtask/{subTaskId}")
    @PreAuthorize("(@permissionEvaluator.activatedAndHasRole('USER') && @permissionEvaluator.hasAccessToProjectTask(#taskId)) or @permissionEvaluator.activatedAndHasRole('MANAGER')")
    public ResponseEntity appendSubTask(@RequestParam TaskRelationshipType type, @PathVariable Long subTaskId, @PathVariable Long taskId) {
        // TODO: 03/01/2024 improve it by adding transaction between endpoints (create+append in one transaction)
        taskManager.appendSubTask(taskId, subTaskId, type);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/assign")
    @PreAuthorize("(@permissionEvaluator.activatedAndHasRole('USER') && @permissionEvaluator.hasAccessToProjectTask(#id)) or @permissionEvaluator.activatedAndHasRole('MANAGER')")
    public ResponseEntity assignTask(@PathVariable Long id) {
        taskManager.assignTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/status")
    @PreAuthorize("(@permissionEvaluator.activatedAndHasRole('USER') && @permissionEvaluator.hasAccessToProjectTask(#body.id)) or @permissionEvaluator.activatedAndHasRole('MANAGER')")
    public ResponseEntity changeStatus(@RequestBody EditTaskRequestDTO<Long> body) {
        taskManager.changeStatus(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("(@permissionEvaluator.activatedAndHasRole('USER') && @permissionEvaluator.hasAccessToProjectTask(#body.id)) or @permissionEvaluator.activatedAndHasRole('MANAGER')")
    public ResponseEntity<APIResponse> edit(@Valid @RequestBody EditTaskRequestDTO<Long> body) {
        return super.edit(body);
    }
}
