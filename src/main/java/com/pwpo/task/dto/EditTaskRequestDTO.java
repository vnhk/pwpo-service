package com.pwpo.task.dto;

import com.pwpo.common.enums.Priority;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.Constants;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.model.edit.Editable;
import com.pwpo.task.enums.TaskType;
import com.pwpo.task.model.EstimableDTO;
import com.pwpo.task.model.TaskHistory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
public class EditTaskRequestDTO<ID extends Serializable> implements Editable<ID>, EstimableDTO {
    @Max(value = 3600)
    @Min(value = 0)
    @NotNull
    protected Integer estimationInHours;
    @Max(value = 60)
    @Min(value = 0)
    @NotNull
    protected Integer estimationInMinutes;
    @NotNull
    private ID id;
    @NotNull
    private Status status;
    @NotNull
    private TaskType type;
    @Size(min = 1, max = Constants.SUMMARY_MAX)
    private String summary;
    private Long assignee;
    @NotNull
    private Long owner;
    @NotNull
    private LocalDate dueDate;
    @NotNull
    private Priority priority;
    @Size(max = Constants.DESCRIPTION_MAX)
    private String description;
    @Max(value = 60 * 3600 + 60)
    @Min(value = 0)
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
