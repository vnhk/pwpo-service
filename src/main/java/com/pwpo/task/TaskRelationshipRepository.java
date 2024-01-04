package com.pwpo.task;

import com.pwpo.common.service.PwpoBaseRepository;
import com.pwpo.task.enums.TaskRelationshipType;
import com.pwpo.task.model.Task;
import com.pwpo.task.model.TaskHistory;
import com.pwpo.task.model.TaskRelationship;

import java.util.List;
import java.util.Optional;


public interface TaskRelationshipRepository extends PwpoBaseRepository<TaskRelationship, Long> {
    List<TaskRelationship> findAllByParentAndType(Task parent, TaskRelationshipType type);
    List<TaskRelationship> findAllByChildAndType(Task child, TaskRelationshipType type);
}
