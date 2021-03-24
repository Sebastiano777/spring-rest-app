package com.sebastian.springrestapp.controllers;

import com.sebastian.springrestapp.payload.AddUserRequestDTO;
import com.sebastian.springrestapp.payload.ModifyUserRequestDTO;
import com.sebastian.springrestapp.payload.UserResponseDTO;
import com.sebastian.springrestapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/vis-test/users", produces = MediaType.APPLICATION_JSON_VALUE)

public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('LIST_USERS')")
    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping()
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody AddUserRequestDTO addUserRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(addUserRequestDTO));
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable int id, @Valid @RequestBody ModifyUserRequestDTO modifyUserRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, modifyUserRequestDTO));
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
