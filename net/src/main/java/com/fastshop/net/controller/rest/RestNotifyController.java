package com.fastshop.net.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Notify;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.NotifyService;

@RestController
public class RestNotifyController {
    @Autowired
    NotifyService notifyService;
    @Autowired
    AccountService accountService;

    @GetMapping("/rest/notifications/{username}")
    public List<Notify> findAll(@PathVariable("username") String username) {
        Account account = accountService.findByUsername(username);
        return notifyService.findAll(account);
    }

    @GetMapping("/rest/notifications/{username}/true")
    public List<Notify> findAllTrue(@PathVariable("username") String username) {
        Account account = accountService.findByUsername(username);
        return notifyService.findAllByAccAndNowAndStatusTrueOrderBy(account);
    }

    @GetMapping("/rest/notifications/{username}/false")
    public List<Notify> findAllFalse(@PathVariable("username") String username) {
        Account account = accountService.findByUsername(username);
        return notifyService.findAllByAccAndNowAndStatusFalseOrderBy(account);
    }

    @GetMapping("/rest/notifications/notify/{id}")
    public Notify findById(@PathVariable("id") Long id) {
        return notifyService.findById(id);
    }

    @PostMapping("/rest/notifications")
    public void save(@RequestBody Notify notify) {
        notifyService.save(notify);
    }

    @PutMapping("/rest/notifications/notify/{id}")
    public void update(@PathVariable("id") Long id) {
        Notify notify = notifyService.findById(id);
        notify.setStatus(false);
        notifyService.save(notify);
    }
}
