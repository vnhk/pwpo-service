package com.pwpo.user.dto;

import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.user.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@AllArgsConstructor
@Setter
public class UserContactDTO implements ItemDTO {
    private String email;
    private String phone;
}
