package com.fastshop.net.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.ATM;
import com.fastshop.net.model.Account;
import com.fastshop.net.repository.ATMDAO;
import com.fastshop.net.service.ATMService;

@Service
public class ATMServiceImpl implements ATMService{
    @Autowired
    ATMDAO atmdao;

    @Override
    public ATM findById(Integer id) {
        try {
            return atmdao.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ATM findByAccount(Account account) {
        try {
            return atmdao.findByAccount(account);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ATM findByNumber(String number) {
        try {
            return atmdao.findByNumber(number);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void save(ATM atm) {
        atm.setValid(false);
        atmdao.save(atm);
    }

    @Override
    public void create(ATM atm) {
        atm.setValid(true);
        atmdao.save(atm);
    }
}
