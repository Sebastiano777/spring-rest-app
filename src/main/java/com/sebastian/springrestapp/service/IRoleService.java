package com.sebastian.springrestapp.service;


import com.sebastian.springrestapp.payload.RoleRequestDTO;
import com.sebastian.springrestapp.payload.RoleResponseDTO;

public interface IRoleService {
    RoleResponseDTO[] getRoles();

    RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);

    RoleResponseDTO updateRole(int id, RoleRequestDTO roleRequestDTO);

    void deleteRole(int id);
}
