package com.pwpo.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnumDTO implements ItemDTO {
    private final String internalName;
    private final String displayName;
}