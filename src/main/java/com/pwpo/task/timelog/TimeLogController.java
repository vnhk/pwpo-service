package com.pwpo.task.timelog;

import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.user.model.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/tasks/task/{id}/timelogs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TimeLogController {
    private final TimeLogManager timeLogManager;

    @GetMapping
    public ResponseEntity<APIResponse> getTimeLogs(@PathVariable Long id, SearchQueryOption options) {
        return new ResponseEntity<>(timeLogManager.getTimeLogs(id, options), HttpStatus.OK);
    }

    @GetMapping("/logged-time")
    public ResponseEntity<Integer> getLoggedTimeForTask(@PathVariable Long id) {
        return new ResponseEntity<>(timeLogManager.getLoggedTime(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addTimeLogForTask(@PathVariable Long id, @RequestBody @Valid TimeLogRequest request) {
        timeLogManager.createTimeLog(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
