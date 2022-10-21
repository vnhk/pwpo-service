package com.pwpo.project.service;

import com.pwpo.common.exception.ValidationException;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.QueryFormat;
import com.pwpo.common.model.diff.CompareResponseDTO;
import com.pwpo.common.model.diff.DiffAttribute;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.search.model.SearchResponse;
import com.pwpo.common.service.DiffService;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.project.Project;
import com.pwpo.project.ProjectHistory;
import com.pwpo.project.ProjectHistoryRepository;
import com.pwpo.project.dto.history.ProjectHistoryDetailsResponseDTO;
import com.pwpo.project.dto.history.ProjectHistoryResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectHistoryManager {
    private final ItemMapper mapper;
    private final ProjectHistoryRepository projectHistoryRepository;
    private final SearchService searchService;
    private final DiffService diffService;


    public APIResponse getHistory(Long id, SearchQueryOption options) {
        options.setEntityToFind(ProjectHistory.class.getName());
        String query = String.format(QueryFormat.PROJECT_BY_ID, id);
        SearchResponse searchResult = searchService.search(query, options);

        return mapper.mapToAPIResponse(searchResult, ProjectHistoryResponseDTO.class);
    }

    public APIResponse getHistoryDetails(Long projectId, Long historyId) {
        Optional<ProjectHistory> opt = projectHistoryRepository.findByProjectIdAndId(projectId, historyId);

        if (opt.isEmpty()) {
            throw new ValidationException("Could not find history for project!");
        }

        ProjectHistory projectHistory = opt.get();

        return mapper.mapToAPIResponse(projectHistory, ProjectHistoryDetailsResponseDTO.class);
    }

    public APIResponse compare(Long projectId, Long historyId) {
        Optional<ProjectHistory> opt = projectHistoryRepository.findByProjectIdAndId(projectId, historyId);

        if (opt.isEmpty()) {
            throw new ValidationException("Could not find history for project!");
        }

        ProjectHistory projectHistory = opt.get();
        Project project = projectHistory.getProject();

        List<DiffAttribute> diffAttributes = new ArrayList<>();

        diffAttributes.add(new DiffAttribute("name", diffService.diff(projectHistory.getName(), project.getName())));
        diffAttributes.add(new DiffAttribute("description", diffService.diff(projectHistory.getDescription(), project.getDescription())));
        diffAttributes.add(new DiffAttribute("shortForm", diffService.diff(projectHistory.getShortForm(), project.getShortForm())));
        diffAttributes.add(new DiffAttribute("summary", diffService.diff(projectHistory.getSummary(), project.getSummary())));
        diffAttributes.add(new DiffAttribute("owner", diffService.diff(projectHistory.getOwner(), project.getOwner().getNick())));
        diffAttributes.add(new DiffAttribute("summary", diffService.diff(projectHistory.getOwner(), project.getOwner().getNick())));
        diffAttributes.add(new DiffAttribute("status", diffService.diff(projectHistory.getStatus().getDisplayName(), project.getStatus().getDisplayName())));

        CompareResponseDTO dto = CompareResponseDTO.builder().historyId(historyId)
                .entityId(projectId)
                .diff(diffAttributes)
                .build();

        return new APIResponse(Collections.singletonList(dto), 1, 1, 1);
    }
}
