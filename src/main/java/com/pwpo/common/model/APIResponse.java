package com.pwpo.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class APIResponse {
    private List<? extends ItemDTO> items;
    private int currentFound;
    private int currentPage;
    private int allFound;
}
