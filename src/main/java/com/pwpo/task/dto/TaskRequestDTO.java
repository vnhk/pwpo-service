package com.pwpo.task.dto;

import com.pwpo.user.model.Constants;
import com.pwpo.user.model.ItemDTO;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Getter
public class TaskRequestDTO implements ItemDTO {
    @NotNull
    private final String type;
    @Size(min = 1, max = Constants.SUMMARY_MAX)
    private final String summary;
    @NotNull
    private final String status;
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
}
