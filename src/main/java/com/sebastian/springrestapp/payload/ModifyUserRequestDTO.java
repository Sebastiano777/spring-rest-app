package com.sebastian.springrestapp.payload;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Builder
@Value
public class ModifyUserRequestDTO {

    @NotBlank(message = "You need to pass username")
    String username;

    @NotBlank(message = "You need to pass role id")
    int roleId;
}


