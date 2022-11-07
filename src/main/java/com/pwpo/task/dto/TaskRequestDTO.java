package com.pwpo.task.dto;

import com.pwpo.common.enums.Priority;
import com.pwpo.common.model.Constants;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.task.enums.TaskType;
import com.pwpo.task.model.EstimableDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class TaskRequestDTO implements ItemDTO, EstimableDTO {
    @NotNull
    protected TaskType type;
    @Size(min = 1, max = Constants.SUMMARY_MAX)
    protected String summary;
    protected Long assignee;
    @NotNull
    protected Long owner;
    @NotNull
    protected LocalDate dueDate;
    @NotNull
    protected Priority priority;
    @Size(max = Constants.DESCRIPTION_MAX)
    protected String description;
    @NotNull
    protected Long project;
    @Max(value = 3600)
    @Min(value = 0)
    @NotNull
    protected Integer estimationInHours;
    @Max(value = 60)
    @Min(value = 0)
    @NotNull
    protected Integer estimationInMinutes;
    @Max(value = 60 * 3600 + 60)
    @Min(value = 0)
    protected Integer estimation;

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
