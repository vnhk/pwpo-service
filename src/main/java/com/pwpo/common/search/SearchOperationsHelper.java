package com.pwpo.common.search;

import com.pwpo.common.search.model.SearchCriteria;
import com.pwpo.common.model.db.BaseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;

public class SearchOperationsHelper {

    public static Predicate notLike(Root<? extends BaseEntity> root, CriteriaBuilder criteriaBuilder, SearchCriteria entityCriterion) {
        return criteriaBuilder.notLike(getExpression(root, entityCriterion.getField()), String.valueOf(entityCriterion.getValue()));
    }

    public static Predicate notEqual(Root<? extends BaseEntity> root, CriteriaBuilder criteriaBuilder, SearchCriteria entityCriterion) {
        return criteriaBuilder.notEqual(getExpression(root, entityCriterion.getField()), entityCriterion.getValue());
    }

    public static Predicate equal(Root<? extends BaseEntity> root, CriteriaBuilder criteriaBuilder, SearchCriteria entityCriterion) {
        return criteriaBuilder.equal(getExpression(root, entityCriterion.getField()), entityCriterion.getValue());
    }

    public static Predicate like(Root<? extends BaseEntity> root, CriteriaBuilder criteriaBuilder, SearchCriteria entityCriterion) {
        return criteriaBuilder.like(getExpression(root, entityCriterion.getField()), String.valueOf(entityCriterion.getValue()));
    }

    public static Path<String> getExpression(Root<? extends BaseEntity> root, String field) {
        String[] subObjects = field.split("\\.");
        Path<String> objectPath = root.get(subObjects[0]);

        for (int i = 1; i < subObjects.length; i++) {
            objectPath = objectPath.get(subObjects[i]);
        }


        return objectPath;
    }

    public static Predicate in(Root<? extends BaseEntity> root, SearchCriteria entityCriterion) {
        return getExpression(root, entityCriterion.getField()).in(Arrays.asList(entityCriterion.getValue()));
    }
}
