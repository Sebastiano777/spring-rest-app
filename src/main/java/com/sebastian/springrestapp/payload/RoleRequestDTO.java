package com.sebastian.springrestapp.payload;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.Collection;


@Builder
@Value
public class RoleRequestDTO {

    @NotBlank(message = "You need to pass role name")
    String name;

    @NotBlank(message = "You need to pass permissions list")
    Collection<String> permissions;
}

