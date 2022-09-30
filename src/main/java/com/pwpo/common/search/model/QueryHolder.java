package com.pwpo.common.search.model;

import lombok.Data;

import javax.persistence.criteria.Predicate;

@Data
public class QueryHolder {
    private Operator operator;
    private String code;
    private String value;
    private Object fstQuery;
    private Object sndQuery;
    private Predicate resultPredicate;
}
