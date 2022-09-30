package com.pwpo.project;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.search.model.SearchResponse;
import com.pwpo.common.service.ItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectManager {
    private final ItemMapper mapper;
    private final ProjectRepository projectRepository;
    private final SearchService searchService;

    public APIResponse getProjects(SearchQueryOption options, Class<? extends ItemDTO> dtoClass) {
        SearchResponse searchResult = searchService.search(null, options);
        return mapper.mapToAPIResponse(searchResult, dtoClass);
    }

    public APIResponse getProjectById(String id, Class<? extends ItemDTO> dtoClass) {
        Optional<Project> project = projectRepository.findById(Long.parseLong(id));
        if (project.isPresent()) {
            return mapper.mapToAPIResponse(project.get(), dtoClass);
        } else {
            throw new RuntimeException("Could not find project!");
        }
    }
}
