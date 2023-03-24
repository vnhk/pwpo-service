package com.pwpo.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotNull
    @Size(max = 50, min = 3)
    private String oldPassword;

    @NotNull
    @Size(max = 50, min = 3)
    private String newPassword;

}
