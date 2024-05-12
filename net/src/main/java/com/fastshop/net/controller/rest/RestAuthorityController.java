package com.fastshop.net.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.service.AuthorityService;

@CrossOrigin("*")
@RestController
public class RestAuthorityController {
    @Autowired
    AuthorityService authorityService;

    @GetMapping("/rest/authorities")
    public List<Authority> getAll(Model model) {
        return authorityService.findAll();
    }

    // lấy tất cả danh sách account có role là staff
    @GetMapping("/rest/authorities/staff")
    public List<Account> getAccountStaff() {
        return authorityService.findAll().stream()
                .filter(auth -> auth.getRole().getId().equals("STAFF"))
                .map(auth -> auth.getAccount())
                .collect(Collectors.toList());
    }

    // lấy tất cả danh sách account có role là staff
    @GetMapping("/rest/authorities/staff/active")
    public List<Authority> getAccountStaffActive() {
        return authorityService.findAll().stream()
                .filter(auth -> auth.getRole().getId().equals("STAFF"))
                .filter(auth -> auth.getAccount().getActive() != null && auth.getAccount().getActive() != false)
                .collect(Collectors.toList());
    }

    // lấy authorities id từ username của account
    @GetMapping("/rest/authorities/account/{username}")
    public Integer getAuthIdByUsername(@PathVariable("username") String username) {
        return authorityService.findAll().stream()
                .filter(auth -> auth.getAccount().getUsername().equals(username))
                .collect(Collectors.toList()).get(0).getId();
    }

    // lấy role của accout từ username trong authorities
    @GetMapping("/rest/authorities/role/{username}")
    public String getRoleByUsername(@PathVariable("username") String username) {
        return authorityService.findAll().stream()
                .filter(auth -> auth.getAccount().getUsername().equals(username))
                .collect(Collectors.toList()).get(0).getRole().getName();
    }

    @PostMapping("/rest/authorities")
    public Authority post(@RequestBody Authority authority) {
        authorityService.save(authority);
        return authority;
    }

    @PutMapping("/rest/authorities/{username}")
    public Authority put(@PathVariable("username") String username, @RequestBody Authority authority) {
        authorityService.save(authority);
        return authority;
    }

    @DeleteMapping("/rest/authorities/{username}")
    public void delete(@PathVariable("username") String username) {
        authorityService.deleteById(getAuthIdByUsername(username));
    }
}
