package com.fastshop.net.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.Account;
import com.fastshop.net.service.AccountService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
public class RestAccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/rest/accounts")
    public List<Account> get(Model model) {
        return accountService.findAll();
    }

    @GetMapping("/rest/accounts/{username}")
    public Account getById(@PathVariable("username") String username) {
        return accountService.findById(username);
    }

    @PostMapping("/rest/accounts")
    public Account post(@RequestBody Account account) {
        accountService.save(account);
        return account;
    }

    @PutMapping("/rest/accounts/{username}")
    public Account put(@PathVariable("username") String username, @RequestBody Account account) {
        accountService.save(account);
        return account;
    }
    
    @DeleteMapping("/rest/accounts/{username}")
    public void delete(@PathVariable("username") String username) {
        accountService.deleteById(username);
    }
}
