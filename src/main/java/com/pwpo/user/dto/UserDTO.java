package com.pwpo.user.dto;

import com.pwpo.common.model.ItemDTO;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDTO implements ItemDTO {
    private final Long id;
    private final String nick;
    private final String fullName;
    private final String projectRole;
}
