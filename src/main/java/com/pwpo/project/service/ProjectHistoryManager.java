package com.pwpo.project.service;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.QueryFormat;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.service.BaseHistoryService;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.project.model.ProjectHistory;
import com.pwpo.project.repository.ProjectHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProjectHistoryManager extends BaseHistoryService<ProjectHistory, Long> {
    private final ProjectHistoryRepository projectHistoryRepository;

    public ProjectHistoryManager(ItemMapper mapper, SearchService searchService, ProjectHistoryRepository repository) {
        super(mapper, searchService);
        this.projectHistoryRepository = repository;
    }

    @Override
    protected void updateWithAttributesFromBaseEntity(BaseHistoryEntity history, APIResponse<? extends ItemDTO> apiResponse) {

    }

    @Override
    protected Optional<? extends BaseHistoryEntity> getHistory(Long entityId, Long historyId) {
        return projectHistoryRepository.findByProjectIdAndId(entityId, historyId);
    }

    @Override
    protected String getQuery(Long id) {
        return String.format(QueryFormat.PROJECT_BY_ID, id);
    }
}
