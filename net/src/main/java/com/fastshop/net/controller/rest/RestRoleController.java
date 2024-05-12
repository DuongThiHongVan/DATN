package com.fastshop.net.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.Role;
import com.fastshop.net.service.RoleService;

@CrossOrigin("*")
@RestController
public class RestRoleController {
    @Autowired
    RoleService roleService;

    @GetMapping("/rest/roles")
    public List<Role> getRoles(Model model) {
        return roleService.findAll();
    }
}
