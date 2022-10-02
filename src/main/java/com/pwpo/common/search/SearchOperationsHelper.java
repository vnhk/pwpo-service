package com.pwpo.common.search;

import com.pwpo.common.search.model.SearchCriteria;
import com.pwpo.task.enums.TaskType;
import com.pwpo.user.model.Itemable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class SearchOperationsHelper {

    public static Predicate notLike(Root<? extends Itemable> root, CriteriaBuilder criteriaBuilder, SearchCriteria entityCriterion) {
        return criteriaBuilder.notLike(getExpression(root, entityCriterion.getField()), String.valueOf(entityCriterion.getValue()));
    }

    public static Predicate notEqual(Root<? extends Itemable> root, CriteriaBuilder criteriaBuilder, SearchCriteria entityCriterion) {
        return criteriaBuilder.notEqual(getExpression(root, entityCriterion.getField()), entityCriterion.getValue());
    }

    public static Predicate equal(Root<? extends Itemable> root, CriteriaBuilder criteriaBuilder, SearchCriteria entityCriterion) {
        return criteriaBuilder.equal(getExpression(root, entityCriterion.getField()), entityCriterion.getValue());
    }

    public static Predicate like(Root<? extends Itemable> root, CriteriaBuilder criteriaBuilder, SearchCriteria entityCriterion) {
        return criteriaBuilder.like(getExpression(root, entityCriterion.getField()), String.valueOf(entityCriterion.getValue()));
    }

    public static Path<String> getExpression(Root<? extends Itemable> root, String field) {
        String[] subObjects = field.split("\\.");
        Path<String> objectPath = root.get(subObjects[0]);

        for (int i = 1; i < subObjects.length; i++) {
            objectPath = objectPath.get(subObjects[i]);
        }


        return objectPath;
    }

    public static Predicate in(Root<? extends Itemable> root, SearchCriteria entityCriterion) {
        return getExpression(root, entityCriterion.getField()).in(Arrays.asList(entityCriterion.getValue()));
    }
}
