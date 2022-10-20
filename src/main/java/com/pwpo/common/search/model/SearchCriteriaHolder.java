package com.pwpo.common.search.model;

import com.pwpo.common.model.BaseEntity;
import lombok.Data;

import java.util.Map;

@Data
public class SearchCriteriaHolder {
    private Class<? extends BaseEntity> entityToFind;
    private QueryHolder holder;
    private Map<QueryMapping, SearchCriteria> searchCriteria;
}
