package com.pwpo.common.search;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.model.SearchResponse;
import com.pwpo.common.search.model.SortDirection;
import com.pwpo.common.service.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/search")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SearchController {
    private final SearchService searchService;
    private final ItemMapper mapper;

    @GetMapping()
    @PostAuthorize("@permissionEvaluator.activatedAndHasRole('MANAGER') or @permissionEvaluator.filterSearch(returnObject.getBody(), #options)")
    public ResponseEntity<APIResponse> search(@RequestParam(required = false) String query,
                                              @RequestParam(required = true) String dto,
                                              SearchQueryOption options) throws NoSuchFieldException, ClassNotFoundException {
        SearchResponse search = searchService.search(query, options);
        APIResponse apiResponse = mapper.mapToAPIResponse(search, (Class<? extends ItemDTO>) Class.forName(dto));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/fields")
    public ResponseEntity<SearchResponse> getFields(@RequestParam(required = true) String entity) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping
    @PostAuthorize("@permissionEvaluator.activatedAndHasRole('MANAGER') or @permissionEvaluator.filterSearch(returnObject.getBody(), #searchRequest.resultType)")
    public ResponseEntity<APIResponse> search(@RequestBody SearchRequest searchRequest) throws NoSuchFieldException, ClassNotFoundException {
        SearchQueryOption defaultOptions = new SearchQueryOption(SortDirection.ASC, "id", 1, 1000, searchRequest.resultType);
        SearchResponse search = searchService.search(searchRequest, defaultOptions);
        APIResponse apiResponse = mapper.mapToAPIResponse(search, ItemMapper.getDefaultDTO(defaultOptions.getEntityToFind()));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
