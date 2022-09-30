package com.pwpo.task.dto;

import com.pwpo.user.dto.UserDTO;
import com.pwpo.user.model.ItemDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class TaskPrimaryResponseDTO implements ItemDTO {
    private final Long id;
    private final String type;
    private final String number;
    private final String summary;
    private final String status;
    private final UserDTO assignee;
    private final UserDTO owner;
    private final LocalDate dueDate;
    private final String priority;
}
