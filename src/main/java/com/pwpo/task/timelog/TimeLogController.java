package com.pwpo.task.timelog;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.search.SearchQueryOption;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/tasks/task/{id}/timelogs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TimeLogController {
    private final TimeLogManager timeLogManager;

    @GetMapping
    @PreAuthorize("(hasRole('USER') && @permissionEvaluator.hasAccessToProjectTask(#id)) or hasRole('MANAGER')")
    public ResponseEntity<APIResponse> getTimeLogs(@PathVariable Long id, SearchQueryOption options) {
        return new ResponseEntity<>(timeLogManager.getTimeLogs(id, options), HttpStatus.OK);
    }

    @GetMapping("/logged-time")
    @PreAuthorize("(hasRole('USER') && @permissionEvaluator.hasAccessToProjectTask(#id)) or hasRole('MANAGER')")
    public ResponseEntity<Integer> getLoggedTimeForTask(@PathVariable Long id) {
        return new ResponseEntity<>(timeLogManager.getLoggedTime(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("(hasRole('USER') && @permissionEvaluator.hasAccessToProjectTask(#id)) or hasRole('MANAGER')")
    public ResponseEntity addTimeLogForTask(@PathVariable Long id, @RequestBody @Valid TimeLogRequest request) {
        timeLogManager.createTimeLog(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
