package com.fastshop.net.service;

import com.fastshop.net.model.ATM;
import com.fastshop.net.model.Account;

public interface ATMService {
    void create(ATM atm);
    void save(ATM atm);
    ATM findById(Integer id);
    ATM findByAccount(Account account);
    ATM findByNumber(String number);
}
