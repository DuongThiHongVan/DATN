package com.fastshop.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Role;
import com.fastshop.net.repository.RoleDAO;
import com.fastshop.net.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleDAO roleDAO;

    @Override
    public List<Role> findAll() {
        return roleDAO.findAll();
    }

    @Override
    public Role findById(String id) {
        return roleDAO.findById(id).get();
    }
}
