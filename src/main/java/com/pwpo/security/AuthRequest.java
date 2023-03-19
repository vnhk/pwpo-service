package com.pwpo.security;

import com.pwpo.user.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {
    @NotNull
    @Size(max = 30)
    private String username;
    @NotNull
    @Size(max = 50)
    private String password;
    @Size(max = 50)
    private String email;
    @Size(max = 50)
    private String firstName;
    @Size(max = 50)
    private String lastName;
    private Set<AccountRole> roles;
}
