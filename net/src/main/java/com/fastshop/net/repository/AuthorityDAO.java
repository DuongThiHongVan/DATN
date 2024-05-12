package com.fastshop.net.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;

public interface AuthorityDAO extends JpaRepository<Authority, Integer>{
    Optional<Authority> findByAccount(Account account);
    void deleteByAccount(Account account);
    @Query("SELECT au.account FROM Authority au WHERE (au.role.id = 'STAFF' AND ( au.account.active != null OR au.account.active = false))")
    List<Account> getEmployee();

    @Query("SELECT au.account FROM Authority au WHERE (au.role.id='STAFF') AND (au.account.id=?1)")
    List<Account> findByKeyword(String keyword);

    @Query("SELECT COUNT(o) FROM Authority o WHERE o.role.id ='USER'")
    Integer countByRoleEqualsUser();
}
