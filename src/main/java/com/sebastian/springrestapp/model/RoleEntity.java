package com.sebastian.springrestapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;


@Entity
@Table(name = "app_role")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<UserEntity> users;

    @ManyToMany
    @JoinTable(name = "roles_permissions", joinColumns = @JoinColumn(
            name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Collection<PermissionEntity> permissions;

    public RoleEntity(String name) {
        this.name = name;
    }

    public RoleEntity(String name, Collection<PermissionEntity> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public RoleEntity(Integer id, String name, Collection<PermissionEntity> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }
}

