package com.pwpo.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class APIResponse<T> {
    private List<T> items;
    private int currentFound;
    private int currentPage;
    private int allFound;
}
