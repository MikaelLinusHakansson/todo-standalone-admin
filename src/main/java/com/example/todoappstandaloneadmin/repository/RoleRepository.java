package com.example.todoappstandaloneadmin.repository;

import com.example.todoappstandaloneadmin.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRoles, Integer> {
    Optional<UserRoles> findByName(String name);
}
