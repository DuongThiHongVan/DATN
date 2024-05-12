package com.fastshop.net.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fastshop.net.model.ATM;
import com.fastshop.net.model.Account;

public interface ATMDAO extends JpaRepository<ATM, Integer> {
    @Query("SELECT o FROM ATM o WHERE o.account = ?1 AND valid = true")
    ATM findByAccount(Account account);

    @Query("SELECT o FROM ATM o WHERE o.number = ?1 AND valid = true")
    ATM findByNumber(String number);
}
