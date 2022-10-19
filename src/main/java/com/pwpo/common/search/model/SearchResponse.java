package com.pwpo.common.search.model;

import com.pwpo.user.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SearchResponse {
    private List<? extends BaseEntity> resultList;
    private Integer currentFound;
    private Integer currentPage;
    private Integer allFound;
}
