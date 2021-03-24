package com.sebastian.springrestapp.service;

import com.sebastian.springrestapp.exception.ApiValidationException;
import com.sebastian.springrestapp.model.PermissionEntity;
import com.sebastian.springrestapp.model.RoleEntity;
import com.sebastian.springrestapp.model.UserEntity;
import com.sebastian.springrestapp.payload.AddUserRequestDTO;
import com.sebastian.springrestapp.payload.ModifyUserRequestDTO;
import com.sebastian.springrestapp.payload.RoleResponseDTO;
import com.sebastian.springrestapp.payload.UserResponseDTO;
import com.sebastian.springrestapp.repository.RoleRepository;
import com.sebastian.springrestapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        return userRepository.findAll().stream().map((this::prepareUserResponseDTO)).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO createUser(@Valid AddUserRequestDTO addUserRequestDTO) {
        if (userRepository.existsByUsername(addUserRequestDTO.getUsername())) {
            throw new ApiValidationException("User with this name already exists");
        }

        RoleEntity userRole = roleRepository.findById(addUserRequestDTO.getRoleId())
                .orElseThrow(() -> new ApiValidationException("Error: Role is not found."));

        UserEntity user = new UserEntity(addUserRequestDTO.getUsername(),
                encoder.encode(addUserRequestDTO.getPassword()), userRole);

        UserEntity savedUser = userRepository.save(user);

        return prepareUserResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO updateUser(int id, @Valid ModifyUserRequestDTO modifyUserRequestDTO) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> {
            throw new ApiValidationException("There is no user with specified id.");
        });

        RoleEntity userRole = roleRepository.findById(modifyUserRequestDTO.getRoleId())
                .orElseThrow(() -> new ApiValidationException("Error: Role is not found."));


        userEntity.setUsername(modifyUserRequestDTO.getUsername());
        userEntity.setRole(userRole);
        UserEntity savedUser = userRepository.save(userEntity);
        return prepareUserResponseDTO(savedUser);
    }

    @Override
    public void deleteUser(int id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> {
            throw new ApiValidationException("There is no user with specified id.");
        });

        userRepository.delete(user);
    }

    private UserResponseDTO prepareUserResponseDTO(UserEntity userEntity) {
        UserResponseDTO.UserResponseDTOBuilder userResponseBuilder = modelMapper.map(userEntity,
                UserResponseDTO.UserResponseDTOBuilder.class);
        RoleResponseDTO.RoleResponseDTOBuilder roleResponseDTOBuilder = modelMapper.map(userEntity.getRole(), RoleResponseDTO.RoleResponseDTOBuilder.class);
        RoleResponseDTO roleResponseDTO = roleResponseDTOBuilder.permissions(userEntity.getRole().getPermissions().stream().
                map(PermissionEntity::getName).collect(Collectors.toList())).build();
        return userResponseBuilder.role(roleResponseDTO).build();
    }
}
