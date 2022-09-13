package com.pwpo.user;

import com.pwpo.common.enums.DataEnum;

public enum ProjectRole implements DataEnum {
    DEVELOPER("Developer"), TESTER("Tester"),
    MANAGER("Manager"), BUSINESS_ANALYST("Business Analyst"),
    ARCHITECT("Architect"), PRODUCT_OWNER("Product Owner");

    private final String displayName;

    ProjectRole(String displayName) {
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
