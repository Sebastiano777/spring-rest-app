package com.sebastian.springrestapp.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum PermissionEnum {
    CREATE_USER,
    UPDATE_USER,
    DELETE_USER,
    LIST_USERS;


    public static Set<String> getNames() {
        return Arrays.stream(PermissionEnum.values()).map(PermissionEnum::name).collect(Collectors.toSet());
    }
}


