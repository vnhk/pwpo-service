package com.pwpo.task.dto;

import com.pwpo.common.enums.Priority;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.model.edit.Editable;
import com.pwpo.task.enums.TaskType;
import com.pwpo.task.model.EstimableDTO;
import com.pwpo.task.model.TaskHistory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
public class EditTaskRequestDTO<ID extends Serializable> implements Editable<ID>, EstimableDTO {
    @NotNull
    protected Integer estimationInHours;
    @NotNull
    protected Integer estimationInMinutes;
    @NotNull
    private ID id;
    private Status status;
    private TaskType type;
    private String summary;
    private Long assignee;
    private Long owner;
    private LocalDate dueDate;
    private Priority priority;
    private String description;
    private Integer estimation;

    @Override
    public ID getEntityId() {
        return id;
    }

    @Override
    public Class<? extends ItemDTO> getResponseDTO() {
        return TaskPrimaryResponseDTO.class;
    }

    @Override
    public Class<? extends BaseHistoryEntity> getHistoryClass() {
        return TaskHistory.class;
    }

    @Override
    public Integer getEstimationValue() {
        return estimation;
    }

    @Override
    public void setEstimationValue(Integer value) {
        estimation = value;
    }

    @Override
    public Integer getEstimationInHoursValue() {
        return estimationInHours;
    }

    @Override
    public Integer getEstimationInMinutesValue() {
        return estimationInMinutes;
    }
}
