package com.pwpo.task.enums;

import com.pwpo.common.enums.DataEnum;

public enum TaskRelationshipType implements DataEnum {
    CHILD_SOLVES("Child Solves"), CHILD_IS_PART_OF("Child is part of");

    private final String displayName;

    TaskRelationshipType(String displayName) {
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
