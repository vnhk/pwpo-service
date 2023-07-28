package com.pwpo.task.timelog;

import com.pwpo.common.service.PwpoBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface TimeLogRepository extends PwpoBaseRepository<TimeLog, Long> {
    Page<TimeLog> findByTaskId(Long id, Pageable pageable);

    @Query("select loggedTimeInMinutes from TimeLog where task.id = ?1")
    List<Integer> findAllLoggedTimeInMinutesByTaskId(Long id);
}
