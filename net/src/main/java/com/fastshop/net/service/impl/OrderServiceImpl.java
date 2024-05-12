package com.fastshop.net.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Order;
import com.fastshop.net.repository.OrderDAO;
import com.fastshop.net.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderDAO orderDAO;

    @Override
    public String findAddressByUsername(Account account) {
        try {
            return orderDAO.findAddressByUsername(account).get();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public void save(Order order) {
        orderDAO.save(order);
    }

    @Override
    public void deleteById(Long id) {
        orderDAO.deleteById(id);
    }

    @Override
    public Order findById(Long id) {
        return orderDAO.findById(id).get();
    }

    @Override
    public List<Order> getAllOfOrderToday(Date date) {
        return orderDAO.findByCreateDate(date);
    }

    @Override
    public List<Order> findNotByCreateDate(Date toNow) {
        return orderDAO.findNotByCreateDate(toNow);
    }

    @Override
    public List<Order> findByCreateDateBetween(Date from, Date to) {
        return orderDAO.findByCreateDateBetween(from, to);
    }

    @Override
    public List<Order> findAllByEmailOrPhone(String email, String phone) {
        return orderDAO.findAllByEmailOrPhone(email, phone);
    }

    @Override
    public List<Order> findAllByPriceBetween() {
        return null;
    }

    @Override
    public List<Order> findByStatus(Integer statusId) {
        return orderDAO.findByStatus(statusId);
    }

    @Override
    public List<Order> findByStatusAndAccount(Integer id, Account account) {
        return orderDAO.findByStatusAndAccount(id, account);
    }

    @Override
    public Double totalRevenueByYear(int year) {
        return orderDAO.totalRevenueByYear(year);
    }

    @Override
    public Integer countOrderSuccess() {
        return orderDAO.countOrderSuccess();
    }

    @Override
    public List<String> getListUsernameOrdered() {
        return orderDAO.getListUsernameOrdered();
    }
}
