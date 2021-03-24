package com.sebastian.springrestapp.service;

import com.sebastian.springrestapp.payload.AddUserRequestDTO;
import com.sebastian.springrestapp.payload.ModifyUserRequestDTO;
import com.sebastian.springrestapp.payload.UserResponseDTO;

import java.util.List;

public interface IUserService {
    List<UserResponseDTO> getUsers();

    UserResponseDTO createUser(AddUserRequestDTO addUserRequestDTO);

    UserResponseDTO updateUser(int id, ModifyUserRequestDTO addUserRequestDTO);

    void deleteUser(int id);
}
