package com.pwpo.common.search.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueryMapping {
    private final String query;
    private final String code;
}
