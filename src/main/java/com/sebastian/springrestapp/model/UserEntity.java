package com.sebastian.springrestapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "app_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    public UserEntity(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password) {
        this.username = username;
        this.password = password;
    }

    public UserEntity(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password, RoleEntity role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserEntity(Integer id, @NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password, RoleEntity role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
