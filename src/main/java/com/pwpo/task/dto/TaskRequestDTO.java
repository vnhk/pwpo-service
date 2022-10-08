package com.pwpo.task.dto;

import com.pwpo.user.model.Constants;
import com.pwpo.user.model.ItemDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Getter
@Setter
public class TaskRequestDTO implements ItemDTO {
    @NotNull
    private final String type;
    @Size(min = 1, max = Constants.SUMMARY_MAX)
    private final String summary;
    private final Long assignee;
    @NotNull
    private final Long owner;
    @NotNull
    private final LocalDate dueDate;
    @NotNull
    private final String priority;
    @Size(max = Constants.DESCRIPTION_MAX)
    private final String description;
    @NotNull
    private final Long project;
    @Max(value = 3600)
    @Min(value = 0)
    @NotNull
    private final Integer estimationInHours;
    @Max(value = 60)
    @Min(value = 0)
    @NotNull
    private final Integer estimationInMinutes;
    @Max(value = 60 * 3600 + 60)
    @Min(value = 0)
    private Integer estimation;
}
