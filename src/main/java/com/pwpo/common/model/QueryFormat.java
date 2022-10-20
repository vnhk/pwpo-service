package com.pwpo.common.model;

public class QueryFormat {
    private QueryFormat() {
    }

    public static final String TASKS_BY_PROJECT_ID = "(project.id EQUALS_OPERATION %d)";
    public static final String ENTITY_BY_ID = "(id EQUALS_OPERATION %d)";
    public static final String PROJECT_BY_ID = "(project.id EQUALS_OPERATION %d)";
}
