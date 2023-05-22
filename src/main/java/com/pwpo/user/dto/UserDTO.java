package com.pwpo.user.dto;

import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.user.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
@Setter
public class UserDTO implements ItemDTO {
    private Long id;
    private String nick;
    private String firstName;
    private String lastName;
    private String email;
    private String projectRole;
    private Set<AccountRole> roles;
}
