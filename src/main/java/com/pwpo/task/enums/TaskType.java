package com.pwpo.task.enums;

import com.pwpo.common.enums.DataEnum;

public enum TaskType implements DataEnum {
    STORY("Story"), OBJECTIVE("Objective"), FEATURE("Feature"), TASK("Task");

    private final String displayName;

    TaskType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
