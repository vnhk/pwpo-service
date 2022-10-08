package com.pwpo.common.search;

import com.pwpo.common.search.model.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NotNull
@Getter
@Setter
public class SearchQueryOption {
    @NotNull
    private SortDirection sortDirection;
    @NotNull
    private String sortField;
    @NotNull
    private Integer page;
    @NotNull
    private Integer pageSize;
    private String entityToFind;
}
