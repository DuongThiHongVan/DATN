package com.fastshop.net.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastshop.net.model.Role;

public interface RoleDAO extends JpaRepository<Role, String>{
    Optional<Role> findByName(String name);
}
