package com.pwpo.task.dto;

import com.pwpo.common.enums.Priority;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.task.enums.TaskType;
import com.pwpo.task.model.EstimableDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class TaskRequestDTO implements ItemDTO, EstimableDTO {
    private TaskType type;
    private String summary;
    private Long assignee;
    private Long owner;
    private LocalDate dueDate;
    private Priority priority;
    private String descriptionHtml;;
    @NotNull
    private Long project;
    @NotNull
    private Integer estimationInHours;
    @NotNull
    private Integer estimationInMinutes;
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
