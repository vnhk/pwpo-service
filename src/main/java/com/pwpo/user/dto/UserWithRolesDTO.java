package com.pwpo.user.dto;

import com.pwpo.common.model.dto.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@Setter
public class UserWithRolesDTO implements ItemDTO {
    private Long id;
    private String nick;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
}
