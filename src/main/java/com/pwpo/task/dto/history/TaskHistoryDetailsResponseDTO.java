package com.pwpo.task.dto.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.user.dto.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
public class TaskHistoryDetailsResponseDTO implements ItemDTO {
    private Long id;
    private String number;
    private String summary;
    private String status;
    private String priority;
    private Integer estimation;
    private LocalDate dueDate;
    private String type;
    private String description;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime expired;
    private String owner;
    private String assignee;
    private UserDTO editor;
}
