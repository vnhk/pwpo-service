package com.pwpo.project.dto;

import com.pwpo.common.model.dto.ItemDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectRequestDTO implements ItemDTO {
    protected String summary;
    protected String name;
    protected String shortForm;
    protected String descriptionHtml;;
    @NotNull
    protected Long owner;
}
