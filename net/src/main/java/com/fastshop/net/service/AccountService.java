package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Account;

public interface AccountService {
    void save(Account account);
    void deleteById(String username);
    List<Account> getAdministrators();
    List<Account> findAll();
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Account findById(String username);
    Account findByEmail(String email);
    Account findByUsername(String username);
    Account findByUsernameOrEmail(String username, String email);
    Account findByUsernameOrEmailAndPassword(String username, String email, String password);
    Boolean existsByUsernameOrEmail(String username, String email);
    
}
