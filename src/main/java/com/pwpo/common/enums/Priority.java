package com.pwpo.common.enums;

public enum Priority implements DataEnum {
    LOW("Low"), MEDIUM("Medium"), HIGH("High"), CRITICAL("Critical");

    private final String displayName;

    Priority(String displayName) {
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
