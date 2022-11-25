package com.pwpo.task.timelog;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Builder
@Getter
@Setter
public class TimeLogRequest {
    @Size(max = 500)
    private final String comment;
    @Max(value = 20)
    @Min(value = 0)
    @NotNull
    private Integer timeInHours;
    private final LocalDate date;
    @Max(value = 60)
    @Min(value = 0)
    @NotNull
    private Integer timeInMinutes;
    @Max(value = 60 * 20 + 60)
    @Min(value = 0)
    private Integer timeInMinutesResult;
}
