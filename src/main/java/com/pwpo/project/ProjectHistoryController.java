package com.pwpo.project;

import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.model.APIResponse;
import com.pwpo.project.service.ProjectHistoryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/projects/{projectId}/history")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectHistoryController {
    private final ProjectHistoryManager projectManager;

    @GetMapping
    public ResponseEntity<APIResponse> getProjectHistory(@PathVariable Long projectId, SearchQueryOption options) {
        return new ResponseEntity<>(projectManager.getHistory(projectId, options), HttpStatus.OK);
    }

    @GetMapping("/{historyId}")
    public ResponseEntity<APIResponse> getProjectHistoryDetails(@PathVariable Long projectId, @PathVariable Long historyId) {
        return new ResponseEntity<>(projectManager.getHistoryDetails(projectId, historyId), HttpStatus.OK);
    }

    @GetMapping("/{historyId}/compare")
    public ResponseEntity<APIResponse> compareProjectWithHistory(@PathVariable Long projectId, @PathVariable Long historyId) {
        return new ResponseEntity<>(projectManager.compare(projectId, historyId), HttpStatus.OK);
    }
}
