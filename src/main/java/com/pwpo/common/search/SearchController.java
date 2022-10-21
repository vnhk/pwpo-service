package com.pwpo.common.search;

import com.pwpo.common.search.model.SearchResponse;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/search")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SearchController {
    private final SearchService searchService;
    private final ItemMapper mapper;

    @GetMapping()
    public ResponseEntity<APIResponse> search(@RequestParam(required = false) String query,
                                              @RequestParam(required = true) String dto,
                                              SearchQueryOption options) throws NoSuchFieldException, ClassNotFoundException {
        /// TODO: 19.10.2022 EXPIRED handling

        SearchResponse search = searchService.search(query, options);
        APIResponse apiResponse = mapper.mapToAPIResponse(search, (Class<? extends ItemDTO>) Class.forName(dto));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/fields")
    public ResponseEntity<SearchResponse> getFields(@RequestParam(required = true) String entity) {
        // TODO: 30.09.2022 implement

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
