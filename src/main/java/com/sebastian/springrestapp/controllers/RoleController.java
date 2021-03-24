package com.sebastian.springrestapp.controllers;

import com.sebastian.springrestapp.payload.RoleRequestDTO;
import com.sebastian.springrestapp.payload.RoleResponseDTO;
import com.sebastian.springrestapp.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/vis-test/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoleController {

    private final IRoleService roleService;

    @Autowired
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<RoleResponseDTO[]> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }

    @PostMapping()
    public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(roleRequestDTO));
    }


    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable int id, @Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.updateRole(id, roleRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

}
