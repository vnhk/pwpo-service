package com.pwpo.task.controller;

import com.pwpo.common.controller.BaseHistoryEntityController;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.HistoryReponseDTO;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.task.dto.history.TaskHistoryDetailsResponseDTO;
import com.pwpo.task.model.TaskHistory;
import com.pwpo.task.service.TaskHistoryManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tasks/{taskId}/history")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}
        , exposedHeaders = "*")
public class TaskHistoryController extends BaseHistoryEntityController<TaskHistory, Long> {
    public TaskHistoryController(TaskHistoryManager historyService) {
        super(historyService);
    }

    @GetMapping
    @PreAuthorize("(@permissionEvaluator.activatedAndHasRole('USER') && @permissionEvaluator.hasAccessToProjectTask(#taskId)) or @permissionEvaluator.activatedAndHasRole('MANAGER')")
    public ResponseEntity<APIResponse> getTaskHistory(@PathVariable Long taskId, SearchQueryOption options) {
        return super.getHistory(taskId, options);
    }

    @GetMapping("/{historyId}")
    @PreAuthorize("(@permissionEvaluator.activatedAndHasRole('USER') && @permissionEvaluator.hasAccessToProjectTask(#taskId)) or @permissionEvaluator.activatedAndHasRole('MANAGER')")
    public ResponseEntity<APIResponse> getTaskHistoryDetails(@PathVariable Long taskId, @PathVariable Long historyId) {
        return super.getHistoryDetails(taskId, historyId);
    }

    @GetMapping("/{historyId}/compare")
    @PreAuthorize("(@permissionEvaluator.activatedAndHasRole('USER') && @permissionEvaluator.hasAccessToProjectTask(#taskId)) or @permissionEvaluator.activatedAndHasRole('MANAGER')")
    public ResponseEntity<APIResponse> compareTaskWithHistory(@PathVariable Long taskId, @PathVariable Long historyId) {
        return super.compareWithHistory(taskId, historyId);
    }

    @Override
    protected Class<? extends ItemDTO> getHistoryDTO() {
        return HistoryReponseDTO.class;
    }

    @Override
    protected Class<? extends ItemDTO> getHistoryDetailsDTO() {
        return TaskHistoryDetailsResponseDTO.class;
    }
}
