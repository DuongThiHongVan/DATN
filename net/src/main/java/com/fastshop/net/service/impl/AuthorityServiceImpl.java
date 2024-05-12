package com.fastshop.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Role;
import com.fastshop.net.repository.AuthorityDAO;
import com.fastshop.net.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService{
    @Autowired
    AuthorityDAO authorityDAO;

    @Override
    public Role getRoleByAccount(Account account) {
        return authorityDAO.findByAccount(account).get().getRole();
    }

    @Override
    public Authority findByAccount(Account account) {
        return authorityDAO.findByAccount(account).get();
    }

    @Override
    public List<Authority> findAll() {
        return authorityDAO.findAll();
    }

    @Override
    public void save(Authority authority) {
        authorityDAO.save(authority);
    }

    @Override
    public void deleteById(Integer id) {
        authorityDAO.deleteById(id);
    }

    @Override
    public void deleteByAuthority(Authority authority) {
        authorityDAO.delete(authority);
    }

    @Override
    public List<Account> getListStaff() {
        return authorityDAO.getEmployee();
    }

    @Override
    public List<Account> findByKeyword(String keyword) {
        return authorityDAO.findByKeyword(keyword);
    }

    @Override
    public Integer countByRoleEqualsUser() {
        return authorityDAO.countByRoleEqualsUser();
    }
}
