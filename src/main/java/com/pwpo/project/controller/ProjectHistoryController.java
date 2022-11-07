package com.pwpo.project.controller;

import com.pwpo.common.controller.BaseHistoryEntityController;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.project.dto.history.ProjectHistoryDetailsResponseDTO;
import com.pwpo.common.model.dto.HistoryReponseDTO;
import com.pwpo.project.model.ProjectHistory;
import com.pwpo.project.service.ProjectHistoryManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/projects/{projectId}/history")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectHistoryController extends BaseHistoryEntityController<ProjectHistory, Long> {
    public ProjectHistoryController(ProjectHistoryManager historyService) {
        super(historyService);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getProjectHistory(@PathVariable Long projectId, SearchQueryOption options) {
        return super.getHistory(projectId, options);
    }

    @GetMapping("/{historyId}")
    public ResponseEntity<APIResponse> getProjectHistoryDetails(@PathVariable Long projectId, @PathVariable Long historyId) {
        return super.getHistoryDetails(projectId, historyId);
    }

    @GetMapping("/{historyId}/compare")
    public ResponseEntity<APIResponse> compareProjectWithHistory(@PathVariable Long projectId, @PathVariable Long historyId) {
        return super.compareWithHistory(projectId, historyId);
    }

    @Override
    protected Class<? extends ItemDTO> getHistoryDTO() {
        return HistoryReponseDTO.class;
    }

    @Override
    protected Class<? extends ItemDTO> getHistoryDetailsDTO() {
        return ProjectHistoryDetailsResponseDTO.class;
    }
}
