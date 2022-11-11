package com.pwpo.task.dto;

import com.pwpo.common.enums.Priority;
import com.pwpo.common.model.Constants;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.task.enums.TaskType;
import com.pwpo.task.model.EstimableDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class TaskRequestDTO implements ItemDTO, EstimableDTO {
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
    @NotNull
    private Long project;
    @Max(value = 3600)
    @Min(value = 0)
    @NotNull
    private Integer estimationInHours;
    @Max(value = 60)
    @Min(value = 0)
    @NotNull
    private Integer estimationInMinutes;
    @Max(value = 60 * 3600 + 60)
    @Min(value = 0)
    private Integer estimation;

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
