package com.pwpo.task.timelog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {
    Page<TimeLog> findByTaskId(Long id, Pageable pageable);

    @Query("select loggedTimeInMinutes from TimeLog where id = ?1")
    List<Integer> findAllLoggedTimeInMinutesByTaskId(Long id);
}
