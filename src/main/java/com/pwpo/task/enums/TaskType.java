package com.pwpo.task.enums;

import com.pwpo.common.enums.DataEnum;

public enum TaskType implements DataEnum {
    STORY("Story"), BUG("Bug"), OBJECTIVE("Objective"), FEATURE("Feature"), TASK("Task");

    private final String displayName;

    TaskType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getInternalName() {
        return name();
    }
}
