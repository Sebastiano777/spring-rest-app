package com.sebastian.springrestapp.repository;

import com.sebastian.springrestapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    boolean findById(String username);
}
