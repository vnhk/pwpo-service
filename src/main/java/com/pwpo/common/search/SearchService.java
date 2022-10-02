package com.pwpo.common.search;

import com.pwpo.common.search.model.*;
import com.pwpo.user.model.Itemable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.*;

@Service
@Slf4j
public class SearchService {
    @PersistenceContext
    protected EntityManager entityManager;
    protected CriteriaBuilder criteriaBuilder;
    private static final Boolean nullFirst = false;

    @PostConstruct
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public SearchResponse search(String query, SearchQueryOption options) {
        validateOptions(options);
        Class<? extends Itemable> entityToFind = getEntityToFind(options);

        SortDirection sortDirection = options.getSortDirection();
        String sortField = options.getSortField();
        Integer page = options.getPage();
        Integer pageSize = options.getPageSize();

        CriteriaQuery<? extends Itemable> criteriaQuery = criteriaBuilder.createQuery(entityToFind);
        Root<? extends Itemable> root = criteriaQuery.from(entityToFind);

        if (StringUtils.isNoneEmpty(query)) {
            criteriaQuery.where(buildMainPredicate(query, root, entityToFind));
        }

        criteriaQuery.orderBy(new OrderImpl(SearchOperationsHelper.getExpression(root, sortField), isAscendingSortDirection(sortDirection), nullFirst));

        TypedQuery<? extends Itemable> resultQuery = entityManager.createQuery(criteriaQuery);
        Integer allFound = getHowManyItemsExist(criteriaQuery, root);

        resultQuery.setFirstResult(pageSize * (page - 1));
        resultQuery.setMaxResults(pageSize);
        List<? extends Itemable> resultList = resultQuery.getResultList();

        return new SearchResponse(resultList, resultList.size(), page, allFound);
    }

    private void validateOptions(SearchQueryOption options) {
        throwIfNullOrEmpty(options.getPage(), "page");
        throwIfNullOrEmpty(options.getPageSize(), "pageSize");
        throwIfNullOrEmpty(options.getSortField(), "sortField");
        throwIfNullOrEmpty(options.getSortDirection(), "sortDirection");
    }

    private void throwIfNullOrEmpty(Object option, String name) {
        if (Objects.isNull(option) || StringHelper.isEmpty(String.valueOf(option))) {
            throw new RuntimeException(String.format("Option %s cannot be empty!", name));
        }
    }

    private Class<? extends Itemable> getEntityToFind(SearchQueryOption options) {
        try {
            return (Class<? extends Itemable>) Class.forName(options.getEntityToFind());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not create query for " + options.getEntityToFind() + "!");
        }
    }

    private Integer getHowManyItemsExist(CriteriaQuery<? extends Itemable> criteriaQuery, Root<? extends Itemable> root) {
        criteriaQuery.select(root.get("id"));
        return entityManager.createQuery(criteriaQuery).getResultList().size();
    }

    private boolean isAscendingSortDirection(SortDirection sortDirection) {
        return sortDirection.equals(SortDirection.ASC);
    }

    private Predicate buildMainPredicate(String query, Root<? extends Itemable> root, Class<? extends Itemable> entityToFind) {
        log.info(String.format("Processing query:[%s]", query));

        List<Operator> operators = new ArrayList<>();
        query = replaceAndAddOperators(query, operators);

        if (operators.isEmpty()) {
            log.info("Query contains one sub query.");
            return buildPredicate(query, root, entityToFind);
        } else {
            log.info("Query contains multiple sub queries.");
            return buildPredicate(query, root, entityToFind, operators);
        }
    }

    private Predicate buildPredicate(String queryWithOneSubQuery, Root<? extends Itemable> root, Class<? extends Itemable> entityToFind) {
        queryWithOneSubQuery = removeParentheses(queryWithOneSubQuery);
        SearchCriteria searchCriteria = buildSearchCriteria(queryWithOneSubQuery);
        log.info(String.format("Criteria created:[%s]", searchCriteria));

        return preparePredicates(root, searchCriteria, entityToFind);
    }

    private Predicate buildPredicate(String query, Root<? extends Itemable> root,
                                     Class<? extends Itemable> entityToFind, List<Operator> operators) {
        SearchCriteriaHolder mainCriteriaHolder = buildCriteriaHolder(query, operators);
        mainCriteriaHolder.setEntityToFind(entityToFind);
        QueryHolder holder = mainCriteriaHolder.getHolder();

        log.info("Building predicates.");
        Predicate predicate = buildPredicates(holder, mainCriteriaHolder, root);
        log.info(String.format("Predicates created:[%s]", predicate.toString()));

        return predicate;
    }

    private Predicate buildPredicates(QueryHolder holder, SearchCriteriaHolder mainCriteriaHolder, Root<? extends Itemable> root) {
        Class<? extends Itemable> entityToFind = mainCriteriaHolder.getEntityToFind();
        SearchCriteria fstSearchCriteria;
        SearchCriteria sndSearchCriteria;

        if (holder.getFstQuery() instanceof String fstQuery && holder.getSndQuery() instanceof String sndQuery) {
            fstSearchCriteria = getSearchCriteria(mainCriteriaHolder, fstQuery);
            sndSearchCriteria = getSearchCriteria(mainCriteriaHolder, sndQuery);

            List<Predicate> predicates = new ArrayList<>(2);
            predicates.add(preparePredicates(root, fstSearchCriteria, entityToFind));
            predicates.add(preparePredicates(root, sndSearchCriteria, entityToFind));

            return executeOperator(holder.getOperator(), predicates);
        } else if (holder.getFstQuery() instanceof String fstQuery) {
            fstSearchCriteria = getSearchCriteria(mainCriteriaHolder, fstQuery);

            List<Predicate> predicates = new ArrayList<>(2);
            predicates.add(preparePredicates(root, fstSearchCriteria, entityToFind));
            predicates.add(buildPredicates((QueryHolder) holder.getSndQuery(), mainCriteriaHolder, root));

            return executeOperator(holder.getOperator(), predicates);
        } else {
            sndSearchCriteria = getSearchCriteria(mainCriteriaHolder, (String) holder.getSndQuery());

            List<Predicate> predicates = new ArrayList<>(2);
            predicates.add(buildPredicates((QueryHolder) holder.getFstQuery(), mainCriteriaHolder, root));
            predicates.add(preparePredicates(root, sndSearchCriteria, entityToFind));

            return executeOperator(holder.getOperator(), predicates);
        }
    }

    private SearchCriteria getSearchCriteria(SearchCriteriaHolder mainCriteriaHolder, String query) {
        return mainCriteriaHolder.getSearchCriteria().entrySet().stream()
                .filter(e -> ("(" + e.getKey().getCode() + ")").equals(query))
                .map(Map.Entry::getValue).findFirst().get();
    }

    private SearchCriteriaHolder buildCriteriaHolder(String queryOrig, List<Operator> operators) {
        log.info("Building criteria holder.");

        SearchCriteriaHolder result = new SearchCriteriaHolder();
        Map<QueryMapping, SearchCriteria> searchCriteria = new HashMap<>();

        String query = queryOrig;

        String[] queries = query.split("LOGIC_OPR");
        log.info(String.format("queries:[%s]", Arrays.toString(queries)));

        int i = 0;
        for (String q : queries) {
            String replacement = "query" + i++;
            q = removeParentheses(q);
            searchCriteria.put(new QueryMapping(q, replacement), buildSearchCriteria(q));
            query = query.replace(q, replacement);

            log.info(String.format("query[%d]:[%s]", i, q));
        }

        QueryHolder holder = buildEntityCriteriaHolder(query, operators, searchCriteria);

        result.setSearchCriteria(searchCriteria);
        result.setHolder(holder);

        return result;
    }

    private String removeParentheses(String q) {
        return q.replace("(", "").replace(")", "").trim();
    }

    private QueryHolder buildEntityCriteriaHolder(String query, List<Operator> operators, Map<QueryMapping, SearchCriteria> searchCriteria) {
        List<QueryMapping> queries = new ArrayList<>();

        log.info(query);

        while (query.contains("LOGIC_OPR")) {
            int size = searchCriteria.size() + queries.size();
            for (int i = 0; i < size; i++) {
                for (int y = 0; y < size; y++) {
                    String connectedSubQuery = "(" + "query" + i + ")" + " LOGIC_OPR " + "(" + "query" + (y + 1) + ")";
                    if (query.contains(connectedSubQuery)) {
                        queries.add(new QueryMapping(connectedSubQuery, "query" + (size)));
                        query = query.replace(connectedSubQuery, "query" + (size));
                    }
                }
            }
        }

        assert operators.size() == queries.size();

        return createQueryHolders(operators, queries, queries.size() - 1);
    }

    private QueryHolder createQueryHolders(List<Operator> operators, List<QueryMapping> queries, int i) {
        QueryHolder holder = new QueryHolder();
        Operator operator = operators.get(i);
        QueryMapping queryMapping = queries.get(i);
        String code = queryMapping.getCode();
        String value = queryMapping.getQuery();
        holder.setCode(code);
        holder.setValue(value);
        holder.setOperator(operator);

        String[] args = holder.getValue().split(" LOGIC_OPR ");

        Optional<QueryMapping> first = queries.stream().filter(e -> ("(" + e.getCode() + ")").equals(args[0]))
                .findFirst();

        Optional<QueryMapping> second = queries.stream().filter(e -> ("(" + e.getCode() + ")").equals(args[1]))
                .findFirst();

        if (first.isEmpty()) {
            holder.setFstQuery(args[0]);
        } else {
            holder.setFstQuery(createQueryHolders(operators, queries, i - 1));
        }

        if (second.isEmpty()) {
            holder.setSndQuery(args[1]);
        } else {
            holder.setSndQuery(createQueryHolders(operators, queries, i - 1));
        }


        return holder;
    }

    private SearchCriteria buildSearchCriteria(String query) {
        SearchCriteria criteria = new SearchCriteria();
        for (SearchOperation searchOperation : SearchOperation.values()) {
            String[] criteriaArguments = query.split(searchOperation.name());
            if (criteriaArguments.length == 2) {
                criteria.setField(criteriaArguments[0].trim());
                criteria.setOperation(searchOperation);
                criteria.setValue(criteriaArguments[1].trim());
                break;
            }
        }
        return criteria;
    }

    private String replaceAndAddOperators(String query, List<Operator> operators) {
        int andIndex;
        int orIndex;
        do {
            andIndex = query.indexOf(Operator.AND_OPERATOR.name());
            orIndex = query.indexOf(Operator.OR_OPERATOR.name());

            if (orIndex == -1 && andIndex == -1) {
                break;
            }

            if (andIndex != -1 && orIndex != -1) {
                if (andIndex < orIndex) {
                    operators.add(Operator.AND_OPERATOR);
                    query = query.replaceFirst(Operator.AND_OPERATOR.name(), "LOGIC_OPR");
                } else {
                    operators.add(Operator.OR_OPERATOR);
                    query = query.replaceFirst(Operator.OR_OPERATOR.name(), "LOGIC_OPR");
                }
            } else if (andIndex != -1) {
                operators.add(Operator.AND_OPERATOR);
                query = query.replaceFirst(Operator.AND_OPERATOR.name(), "LOGIC_OPR");
            } else {
                operators.add(Operator.OR_OPERATOR);
                query = query.replaceFirst(Operator.OR_OPERATOR.name(), "LOGIC_OPR");
            }
        } while (true);

        return query;
    }

    private Predicate preparePredicates(Root<? extends Itemable> root, SearchCriteria searchCriteria, Class<? extends Itemable> entityToFind) {
        try {
            return execute(root, searchCriteria, entityToFind);
        } catch (NoSuchFieldException e) {
            log.error("Could not parse query, used field is not supported!", e);
            throw new RuntimeException("Could not parse query, used field is not supported!");
        }
    }

    private Predicate executeOperator(Operator operator, List<Predicate> predicates) {
        if (operator.equals(Operator.AND_OPERATOR)) {
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        } else if (operator.equals(Operator.OR_OPERATOR)) {
            return criteriaBuilder.or(predicates.toArray(Predicate[]::new));
        }

        throw new IllegalArgumentException("Invalid operator!");
    }

    private Predicate execute(Root<? extends Itemable> root, SearchCriteria entityCriterion, Class<? extends Itemable> entity) throws NoSuchFieldException {
        Object value;
        String[] subObjects = entityCriterion.getField().split("\\.");
        Field field = entity.getDeclaredField(subObjects[0]);

        for (int i = 1; i < subObjects.length; i++) {
            field = field.getType().getDeclaredField(subObjects[i]);
        }

        if (entityCriterion.getOperation().equals(SearchOperation.IN_OPERATION)) {
            setValueAsArray(entityCriterion, field);
            return SearchOperationsHelper.in(root, entityCriterion);
        } else {
            value = getValue(entityCriterion.getValue(), field);
        }

        entityCriterion.setValue(value);

        Predicate result;
        switch (entityCriterion.getOperation()) {
            case EQUALS_OPERATION -> result = SearchOperationsHelper.equal(root, criteriaBuilder, entityCriterion);
            case LIKE_OPERATION -> result = SearchOperationsHelper.like(root, criteriaBuilder, entityCriterion);
            case NOT_EQUALS_OPERATION -> result = SearchOperationsHelper.notEqual(root, criteriaBuilder, entityCriterion);
            case NOT_LIKE_OPERATION -> result = SearchOperationsHelper.notLike(root, criteriaBuilder, entityCriterion);
            default -> throw new IllegalArgumentException("Invalid SearchCriteria operation!");
        }

        return result;
    }

    private Object getValue(Object value, Field field) {
        if (field.getType().isEnum()) {
            value = getEnumValue(value, field);
        } else {
            value = getPrimitiveTypeValue(value, field);
        }
        return value;
    }

    private void setValueAsArray(SearchCriteria entityCriterion, Field field) {
        String[] val = String.valueOf(entityCriterion.getValue()).split(",");
        List<Object> values = new ArrayList<>();

        for (int i = 0; i < val.length; i++) {
            values.add(getValue(val[i], field));
        }

        entityCriterion.setValue(values);
    }

    private Enum getEnumValue(Object value, Field field) {
        Optional<?> el = Arrays.stream(field.getType().getEnumConstants())
                .filter(e -> e.toString().equals(value))
                .findFirst();
        return (Enum) el.orElse(null);
    }

    private static Object getPrimitiveTypeValue(Object value, Field field) {
        String valueAsString = String.valueOf(value);
        if (field.getAnnotatedType().getType().equals(Long.class)) {
            value = Long.valueOf(valueAsString);
        } else if (field.getAnnotatedType().getType().equals(String.class)) {
            value = valueAsString;
        } else if (field.getAnnotatedType().getType().equals(Integer.class)) {
            value = Integer.valueOf(valueAsString);
        } else if (field.getAnnotatedType().getType().equals(Double.class)) {
            value = Double.valueOf(valueAsString);
        }
        return value;
    }
}
