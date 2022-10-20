package com.pwpo.task;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/projects/project/{id}/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectTaskController {
    private final TaskManager taskManager;

    @GetMapping
    public ResponseEntity<APIResponse> getTasksByProjectId(@PathVariable String id, SearchQueryOption options, String dto) throws ClassNotFoundException {
        return new ResponseEntity<>(taskManager.getTasksByProjectId(id, options, (Class<? extends ItemDTO>) Class.forName(dto)), HttpStatus.OK);
    }
}
