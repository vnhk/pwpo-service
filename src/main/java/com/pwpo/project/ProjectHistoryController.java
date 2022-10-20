package com.pwpo.project;

import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.model.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/projects/{id}/history")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectHistoryController {
    private final ProjectManager projectManager;

    @GetMapping
    public ResponseEntity<APIResponse> getProjectHistory(@PathVariable Long id, SearchQueryOption options) {
        return new ResponseEntity<>(projectManager.getHistory(id, options), HttpStatus.OK);
    }
}
