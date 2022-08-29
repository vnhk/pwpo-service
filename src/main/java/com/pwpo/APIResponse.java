package com.pwpo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class APIResponse {
    protected List<? extends ItemDTO> items;
    protected int totalCount;
}
