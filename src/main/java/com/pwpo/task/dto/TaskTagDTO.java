package com.pwpo.task.dto;

import com.pwpo.common.model.dto.ItemDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskTagDTO implements ItemDTO {
    private Long id;
    private List<String> names;
}
