package com.fastshop.net.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.ATM;
import com.fastshop.net.model.Account;
import com.fastshop.net.service.ATMService;
import com.fastshop.net.service.AccountService;

@RestController
public class RestATMController {
    @Autowired
    AccountService accountService;
    @Autowired
    ATMService atmService;

    @GetMapping("/rest/atm/{accountId}")
    public ATM findAll(@PathVariable("accountId") String username) {
        Account account = accountService.findById(username);
        return atmService.findByAccount(account);
    }


    @PostMapping("/rest/atm")
    public ATM create(@RequestBody ATM atm) {
        atmService.create(atm);
        return atm;
    }

    @PutMapping("/rest/atm/{accountId}")
    public ATM update(@PathVariable("accountId") String username, @RequestBody ATM atm) {
        Account account = accountService.findById(username);
        if (atmService.findByAccount(account) != null) {
            atmService.save(atm);
        }
        return atm;
    }
}
