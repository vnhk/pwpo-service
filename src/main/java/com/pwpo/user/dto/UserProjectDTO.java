package com.pwpo.user.dto;

import com.pwpo.user.ProjectRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class UserProjectDTO {
   @NotNull
   private Long user;
   @NotNull
   private ProjectRole projectRole;
}
