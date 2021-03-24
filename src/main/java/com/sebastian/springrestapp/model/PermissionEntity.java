package com.sebastian.springrestapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Collection<RoleEntity> roles;

    public PermissionEntity(String name) {
        this.name = name;
    }

}
