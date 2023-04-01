package com.pwpo.common.service;

import com.pwpo.common.diff.DiffService;
import com.pwpo.common.exception.ValidationException;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.HistoryField;
import com.pwpo.common.model.db.BaseEntity;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.model.db.Persistable;
import com.pwpo.common.model.diff.CompareResponseDTO;
import com.pwpo.common.model.diff.DiffAttribute;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.search.model.SearchResponse;
import com.pwpo.task.dto.history.TaskHistoryDetailsResponseDTO;
import com.pwpo.task.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseHistoryService<T extends Persistable, ID extends Serializable> {
    protected final ItemMapper mapper;
    protected final SearchService searchService;
    protected final DiffService diffService;


    public APIResponse getHistory(ID id, SearchQueryOption options, Class<? extends ItemDTO> dto) {
        options.setEntityToFind(getEntityToFind());
        String query = getQuery(id);
        SearchResponse searchResult = searchService.search(query, options);

        return mapper.mapToAPIResponse(searchResult, dto);
    }

    public APIResponse getHistoryDetails(ID entityId, ID historyId, Class<? extends ItemDTO> dto) {
        BaseHistoryEntity history = getHistoryEntity(entityId, historyId);
        Task task = (Task) history.getTargetEntity();

        APIResponse<? extends ItemDTO> apiResponse = mapper.mapToAPIResponse(history, dto);
        TaskHistoryDetailsResponseDTO taskHistoryDetailsResponse = (TaskHistoryDetailsResponseDTO) apiResponse.getItems().get(0);
        taskHistoryDetailsResponse.setNumber(task.getNumber());

        return apiResponse;
    }

    public APIResponse compare(ID entityId, ID historyId) {
        try {
            BaseHistoryEntity history = getHistoryEntity(entityId, historyId);
            BaseEntity entity = history.getTargetEntity();

            List<DiffAttribute> diffAttributes = new ArrayList<>();
            List<Field> historyFields = getHistoryFields(history.getClass());
            for (Field historyField : historyFields) {
                Field entityField = entity.getClass().getDeclaredField(historyField.getName());
                HistoryField annotation = historyField.getAnnotation(HistoryField.class);
                diffAttributes.add(new DiffAttribute(historyField.getName(),
                        diffService.diff(getHistoryFieldValue(historyField, history), getEntityFieldValue(entityField, entity, annotation))));
            }

            CompareResponseDTO dto = CompareResponseDTO.builder().historyId(historyId)
                    .entityId(entityId)
                    .diff(diffAttributes)
                    .build();

            return new APIResponse(Collections.singletonList(dto), 1, 1, 1);

        } catch (Exception e) {
            log.error("Diff cannot be performed!", e);
            throw new RuntimeException("Diff cannot be performed!");
        }
    }

    protected abstract Optional<? extends BaseHistoryEntity> getHistory(ID entityId, ID historyId);

    protected abstract String getQuery(ID id);

    /**
     * @param field      BaseEntity main field with substitute in BaseHistoryEntity field that is annotated
     *                   with @HistoryField, for example String name / Status status / Project project
     * @param entity     BaseEntity object
     * @param annotation HistoryField annotation with path
     * @return field value as String
     * @throws IllegalAccessException when field is not accessible
     * @throws NoSuchFieldException   when path is invalid
     */
    private String getEntityFieldValue(Field field, BaseEntity entity, HistoryField annotation) throws IllegalAccessException, NoSuchFieldException {
        String path = annotation.comparePath();
        if (StringUtils.isNotBlank(path)) {
            return getFieldValueByAnnotationPath(field, entity, path);
        }

        field.setAccessible(true);
        String value = String.valueOf(field.get(entity));
        field.setAccessible(false);

        return value;
    }

    /**
     * History fields in BaseHistoryEntity classes must be of String type or with toString() method (Enums).
     * Therefore, no additional logic is needed to obtain their value
     *
     * @param field  BaseHistoryEntity field
     * @param entity BaseHistoryEntity
     * @return field value as String
     * @throws IllegalAccessException when field is not accessible
     */
    private String getHistoryFieldValue(Field field, BaseHistoryEntity entity) throws IllegalAccessException {
        field.setAccessible(true);
        String value = String.valueOf(field.get(entity));
        field.setAccessible(false);

        return value;
    }

    /**
     * When performing the diff, sometimes the algorithm encounters a BaseEntity complex field (eq. Status / Project / User).
     * Diff service requires all comparable data to be text. @HistoryField annotation has a path attribute that can be used to define field.
     *
     * @param field  main field annotated with @HistoryField, for example String name / Status status / Project project
     * @param object BaseHistory or BaseEntity object
     * @return field value based on the @HistoryField path, if path is blank then main @param field is returned
     * @throws IllegalAccessException when field is not accessible
     * @throws NoSuchFieldException   when path is invalid
     */
    private String getFieldValueByAnnotationPath(Field field, Object object, String path) throws IllegalAccessException, NoSuchFieldException {
        if (StringUtils.isNotBlank(path)) {
            for (String p : path.split("\\.")) {
                field.setAccessible(true);
                object = field.get(object);
                field.setAccessible(false);
                field = object.getClass().getDeclaredField(p);
            }
        }

        field.setAccessible(true);
        String res = String.valueOf(field.get(object));
        field.setAccessible(false);

        return res;
    }


    /**
     * Fields that can be compared have to be annotated with @HistoryField with comparable attribute set to true
     *
     * @param historyClass BaseHistory class
     * @return comparable fields annotated with @HistoryField
     */
    private List<Field> getHistoryFields(Class<? extends BaseHistoryEntity> historyClass) {
        return Arrays.stream(historyClass.getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(HistoryField.class))
                .filter(e -> e.getAnnotation(HistoryField.class).comparable())
                .collect(Collectors.toList());
    }

    private BaseHistoryEntity getHistoryEntity(ID entityId, ID historyId) {
        Optional<? extends BaseHistoryEntity> opt = getHistory(entityId, historyId);

        if (opt.isEmpty()) {
            throw new ValidationException("Could not find history!");
        }
        return opt.get();
    }

    private String getEntityToFind() {
        return ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
    }
}
