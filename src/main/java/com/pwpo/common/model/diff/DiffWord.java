package com.pwpo.common.model.diff;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DiffWord {
    private final String value;
    private final DiffType type;

    @Override
    public String toString() {
        return type.getDisplayName() + value;
    }
}
