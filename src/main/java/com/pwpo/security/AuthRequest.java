package com.pwpo.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
