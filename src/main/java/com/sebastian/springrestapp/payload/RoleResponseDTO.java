package com.sebastian.springrestapp.payload;

import lombok.Builder;
import lombok.Value;

import java.util.Collection;

@Builder
@Value
public class RoleResponseDTO {
    int id;

    String name;

    Collection<String> permissions;

}

