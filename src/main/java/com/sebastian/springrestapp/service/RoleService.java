package com.sebastian.springrestapp.service;

import com.sebastian.springrestapp.exception.ApiValidationException;
import com.sebastian.springrestapp.model.PermissionEntity;
import com.sebastian.springrestapp.model.RoleEntity;
import com.sebastian.springrestapp.payload.RoleRequestDTO;
import com.sebastian.springrestapp.payload.RoleResponseDTO;
import com.sebastian.springrestapp.repository.PermissionRepository;
import com.sebastian.springrestapp.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleResponseDTO[] getRoles() {
        return roleRepository.findAll().stream().map(this::prepareRoleResponseDTO)
                .toArray(RoleResponseDTO[]::new);
    }

    @Override
    public RoleResponseDTO createRole(@Valid RoleRequestDTO roleRequestDTO) {
        if (roleRepository.existsByName(roleRequestDTO.getName())) {
            throw new ApiValidationException("Role with this name already exists");
        }

        List<PermissionEntity> permissionEntities = new ArrayList<>();
        for (String permission : roleRequestDTO.getPermissions()) {
            permissionEntities.add(permissionRepository.findByName(permission).orElseThrow(() -> new ApiValidationException("Incorrect permissions!")));
        }

        RoleEntity role = new RoleEntity(roleRequestDTO.getName(), permissionEntities);

        RoleEntity savedRole = roleRepository.save(role);

        return prepareRoleResponseDTO(savedRole);
    }

    @Override
    public RoleResponseDTO updateRole(int id, @Valid RoleRequestDTO roleRequestDTO) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(() -> {
            throw new ApiValidationException("There is no role with specified id.");
        });

        List<PermissionEntity> permissionEntities = new ArrayList<>();
        for (String permission : roleRequestDTO.getPermissions()) {
            permissionEntities.add(permissionRepository.findByName(permission).orElseThrow(() -> new ApiValidationException("Incorrect permissions!")));
        }
        role.setName(roleRequestDTO.getName());
        role.setPermissions(permissionEntities);
        RoleEntity savedRole = roleRepository.save(role);
        return prepareRoleResponseDTO(savedRole);
    }

    @Override
    public void deleteRole(int id) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(() -> {
            throw new ApiValidationException("There is no role with specified id.");
        });
        roleRepository.delete(role);
    }

    private RoleResponseDTO prepareRoleResponseDTO(RoleEntity roleEntity) {
        RoleResponseDTO.RoleResponseDTOBuilder roleResponseDTOBuilder = modelMapper.map(roleEntity,
                RoleResponseDTO.RoleResponseDTOBuilder.class);
        List<String> permissions = roleEntity.getPermissions().stream().map(PermissionEntity::getName)
                .collect(Collectors.toList());
        return roleResponseDTOBuilder.permissions(permissions).build();
    }
}
