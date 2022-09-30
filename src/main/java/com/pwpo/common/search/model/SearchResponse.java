package com.pwpo.common.search.model;

import com.pwpo.user.model.Itemable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SearchResponse {
    private List<? extends Itemable> resultList;
    private Integer currentFound;
    private Integer currentPage;
    private Integer allFound;
}
