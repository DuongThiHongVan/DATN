package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Role;

public interface RoleService {
    List<Role> findAll();
    Role findById(String id);
}
