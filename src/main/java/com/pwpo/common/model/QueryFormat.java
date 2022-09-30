package com.pwpo.common.model;

public class QueryFormat {
    private QueryFormat() {
    }

    public static final String TASKS_BY_PROJECT_ID = "(project.id EQUALS %d)";
    public static final String ENTITY_BY_ID = "(id EQUALS %d)";
}
