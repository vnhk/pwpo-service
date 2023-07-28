package com.pwpo.common.search.model;

import com.bervan.history.model.Persistable;;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SearchResponse {
    private List<? extends Persistable> resultList;
    private Integer currentFound;
    private Integer currentPage;
    private Integer allFound;
}
