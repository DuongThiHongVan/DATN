package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Notify;

public interface NotifyDAO extends JpaRepository<Notify, Long>{
    @Query("SELECT o FROM Notify o WHERE o.account = ?1 AND o.status = true ORDER BY o.sentDate DESC")
    List<Notify> findAllByAccAndNowAndStatusTrueOrderBy(Account account);

    @Query("SELECT o FROM Notify o WHERE o.account = ?1 AND o.status = false ORDER BY o.sentDate DESC")
    List<Notify> findAllByAccAndNowAndStatusFalseOrderBy(Account account);

    @Query("SELECT o FROM Notify o WHERE o.account = ?1 ORDER BY o.sentDate DESC")
    List<Notify> findAllByAccount(Account account);
}
