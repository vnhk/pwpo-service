package com.pwpo.common.service;

import com.bervan.history.diff.model.DiffAttribute;
import com.bervan.history.model.AbstractBaseEntity;
import com.bervan.history.model.AbstractBaseHistoryEntity;
import com.bervan.history.model.Persistable;
import com.bervan.history.service.HistoryService;
import com.pwpo.common.exception.ValidationException;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.diff.CompareResponseDTO;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.search.model.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseHistoryService<T extends Persistable, ID extends Serializable> extends HistoryService<ID> {
    protected final ItemMapper mapper;
    protected final SearchService searchService;

    public APIResponse getHistory(ID id, SearchQueryOption options, Class<? extends ItemDTO> dto) {
        options.setEntityToFind(getEntityToFind());
        String query = getQuery(id);
        SearchResponse searchResult = searchService.search(query, options);

        return mapper.mapToAPIResponse(searchResult, dto);
    }

    public APIResponse getHistoryDetails(ID entityId, ID historyId, Class<? extends ItemDTO> dto) {
        AbstractBaseHistoryEntity<ID> history = getHistoryEntity(entityId, historyId);

        APIResponse<? extends ItemDTO> apiResponse = mapper.mapToAPIResponse(history, dto);

        updateWithAttributesFromBaseEntity(history, apiResponse);

        return apiResponse;
    }

    protected abstract void updateWithAttributesFromBaseEntity(AbstractBaseHistoryEntity<ID> history, APIResponse<? extends ItemDTO> apiResponse);

    public APIResponse compare(ID entityId, ID historyId) {
        try {
            AbstractBaseHistoryEntity<ID> history = getHistoryEntity(entityId, historyId);
            AbstractBaseEntity<ID> entity = history.getEntity();
            List<DiffAttribute> diffAttributes = super.compare(entity, history);

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

    protected abstract Optional<? extends AbstractBaseHistoryEntity<ID>> getHistory(ID entityId, ID historyId);

    protected abstract String getQuery(ID id);

    private AbstractBaseHistoryEntity<ID> getHistoryEntity(ID entityId, ID historyId) {
        Optional<? extends AbstractBaseHistoryEntity<ID>> opt = getHistory(entityId, historyId);

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
