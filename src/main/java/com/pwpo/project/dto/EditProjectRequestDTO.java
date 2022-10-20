package com.pwpo.project.dto;

import com.pwpo.common.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EditProjectRequestDTO extends ProjectRequestDTO {
    @NotNull
    private Long id;
    @NotNull
    private Status status;
}
