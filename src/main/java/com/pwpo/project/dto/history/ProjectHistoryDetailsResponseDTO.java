package com.pwpo.project.dto.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.user.dto.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ProjectHistoryDetailsResponseDTO implements ItemDTO {
    private final Long id;
    private final String name;
    private final String shortForm;
    private final String summary;
    private final String status;
    private final String description;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime expired;
    private final String owner;
    private final UserDTO editor;
}
