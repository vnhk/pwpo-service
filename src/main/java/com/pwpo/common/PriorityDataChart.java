package com.pwpo.common;

import com.pwpo.common.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PriorityDataChart {
    private final Priority priority;
    private final Long value;
}
