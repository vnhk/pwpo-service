package com.pwpo.project.dto;

import com.pwpo.user.model.Constants;
import com.pwpo.user.model.ItemDTO;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Getter
public class ProjectRequestDTO implements ItemDTO {
    @Size(min = 1, max = Constants.SUMMARY_MAX)
    private final String summary;
    @Size(min = 1, max = Constants.NAME_MAX)
    private final String name;
    @Size(min = 1, max = Constants.SHORT_FORM_MAX)
    private String shortForm;
    @NotNull
    private final Long owner;
    @Size(max = Constants.DESCRIPTION_MAX)
    private final String description;
}
