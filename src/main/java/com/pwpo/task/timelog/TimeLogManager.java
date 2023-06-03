package com.pwpo.task.timelog;

import com.pwpo.common.exception.ValidationException;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.task.TaskRepository;
import com.pwpo.task.model.Task;
import com.pwpo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeLogManager {

    private final TimeLogRepository timeLogRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ItemMapper mapper;

    public APIResponse getTimeLogs(Long id, SearchQueryOption options) {
        PageRequest of = PageRequest.of(options.getPage() - 1, options.getPageSize(), getSortBy());

        Page<TimeLog> timeLogs = timeLogRepository.findByTaskId(id, of);

        List<ItemDTO> result = new ArrayList<>();
        for (TimeLog timeLog : timeLogs) {
            TimeLogDTO itemDTO = (TimeLogDTO) mapper.mapToDTO(timeLog, TimeLogDTO.class);

            result.add(itemDTO);
        }

        return new APIResponse(result, result.size(), options.getPage(), Math.toIntExact(timeLogs.getTotalElements()));
    }

    public Integer getLoggedTime(Long id) {
        List<Integer> timeLogs = timeLogRepository.findAllLoggedTimeInMinutesByTaskId(id);

        Integer result = 0;
        for (Integer timeLog : timeLogs) {
            result += timeLog;
        }

        return result;
    }

    private Sort getSortBy() {
        return Sort.by("date").descending();
    }

    public void createTimeLog(Long taskId, TimeLogRequest timeLogRequest) {
        Long userId = getLoggedUserId();
        validate(taskId, timeLogRequest);
        setLoggedTime(timeLogRequest);

        TimeLog timeLog = new TimeLog();
        timeLog.setLoggedTimeInMinutes(timeLogRequest.getTimeInMinutesResult());
        timeLog.setTask(taskRepository.findById(taskId).get());
        timeLog.setComment(timeLogRequest.getComment());
        timeLog.setUser(userRepository.findById(userId).get());
        timeLog.setCreated(LocalDateTime.now());
        timeLog.setDate(timeLogRequest.getDate());

        timeLogRepository.save(timeLog);
    }

    private Long getLoggedUserId() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByNick(principal.getUsername()).get().getId();
    }

    private void setLoggedTime(TimeLogRequest body) {
        if (body.getTimeInMinutesResult() == null) {
            body.setTimeInMinutesResult(body.getTimeInHours() * 60 + body.getTimeInMinutes());
        }
    }

    private void validate(Long taskId, TimeLogRequest timeLogRequest) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isEmpty()) {
            throw new ValidationException("taskId", "Task with given id does not exist!");
        }

        if ((timeLogRequest.getTimeInMinutes() == null || timeLogRequest.getTimeInMinutes() == 0)
                && (timeLogRequest.getTimeInHours() == null || timeLogRequest.getTimeInHours() == 0)
                && (timeLogRequest.getTimeInMinutesResult() == null || timeLogRequest.getTimeInMinutesResult() == 0)) {
            throw new ValidationException("timeInMinutes", "You have to log at least 1 minute!");
        }
    }
}
