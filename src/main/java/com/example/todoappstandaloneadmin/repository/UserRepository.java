package com.example.todoappstandaloneadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todoappstandaloneadmin.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
}
