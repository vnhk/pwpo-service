package com.pwpo.common.enums;

public enum Status implements DataEnum {
    NEW("New"), IN_PROGRESS("In Progress"), DONE("Done"), CANCELED("Canceled");

    private final String displayName;

    Status(String displayName) {
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
}
