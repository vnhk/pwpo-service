package com.pwpo.user.dto;

import com.pwpo.user.model.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor
@Setter
public class UserDTO implements ItemDTO {
    private final Long id;
    private final String nick;
    private final String fullName;
    private final String projectRole;
}
