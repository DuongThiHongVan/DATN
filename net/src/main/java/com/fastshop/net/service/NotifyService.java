package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Notify;

public interface NotifyService {
    List<Notify> findAll(Account account);
    List<Notify> findAllByAccAndNowAndStatusTrueOrderBy(Account account);
    List<Notify> findAllByAccAndNowAndStatusFalseOrderBy(Account account);
    Notify findById(Long id);
    void save(Notify notify);
    void deleteById(Long id);
    void delete(Notify notify);
}
