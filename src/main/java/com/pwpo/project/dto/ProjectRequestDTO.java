package com.pwpo.project.dto;

import com.pwpo.common.model.Constants;
import com.pwpo.common.model.dto.ItemDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProjectRequestDTO implements ItemDTO {
    @Size(min = 1, max = Constants.SUMMARY_MAX)
    protected String summary;
    @Size(min = 1, max = Constants.NAME_MAX)
    protected String name;
    @Size(min = 1, max = Constants.SHORT_FORM_MAX)
    protected String shortForm;
    @NotNull
    protected Long owner;
    @Size(max = Constants.DESCRIPTION_MAX)
    protected String description;
}
