package com.pwpo.task.dto;

import com.pwpo.common.model.dto.ItemDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TagDTO implements ItemDTO {
    private Long id;
    @NotNull
    @Size(min = 3, max = 35)
    private String name;
    private String summary;
}
