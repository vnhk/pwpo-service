package com.pwpo.common.controller;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.db.Persistable;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.service.BaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@RequiredArgsConstructor
public abstract class BaseHistoryEntityController<T extends Persistable, ID extends Serializable> {
    private final BaseHistoryService<T, ID> historyService;

    protected ResponseEntity<APIResponse> getHistory(ID entityId, SearchQueryOption options) {
        return new ResponseEntity<>(historyService.getHistory(entityId, options, getHistoryDTO()), HttpStatus.OK);
    }

    protected ResponseEntity<APIResponse> getHistoryDetails(ID entityId, ID historyId) {
        return new ResponseEntity<>(historyService.getHistoryDetails(entityId, historyId, getHistoryDetailsDTO()), HttpStatus.OK);
    }

    protected ResponseEntity<APIResponse> compareWithHistory(ID entityId, ID historyId) {
        return new ResponseEntity<>(historyService.compare(entityId, historyId), HttpStatus.OK);
    }

    protected abstract Class<? extends ItemDTO> getHistoryDTO();
    protected abstract Class<? extends ItemDTO> getHistoryDetailsDTO();
}
