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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tasks/{taskId}/history")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskHistoryController extends BaseHistoryEntityController<TaskHistory, Long> {
    public TaskHistoryController(TaskHistoryManager historyService) {
        super(historyService);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getProjectHistory(@PathVariable Long taskId, SearchQueryOption options) {
        return super.getHistory(taskId, options);
    }

    @GetMapping("/{historyId}")
    public ResponseEntity<APIResponse> getProjectHistoryDetails(@PathVariable Long taskId, @PathVariable Long historyId) {
        return super.getHistoryDetails(taskId, historyId);
    }

    @GetMapping("/{historyId}/compare")
    public ResponseEntity<APIResponse> compareProjectWithHistory(@PathVariable Long taskId, @PathVariable Long historyId) {
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
