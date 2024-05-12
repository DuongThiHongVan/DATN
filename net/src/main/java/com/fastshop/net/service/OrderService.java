package com.fastshop.net.service;

import java.util.Date;
import java.util.List;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Order;

public interface OrderService {
    void save(Order order);
    void deleteById(Long id);
    Order findById(Long id);
    Double totalRevenueByYear(int year);
    Integer countOrderSuccess();
    String findAddressByUsername(Account account);
    List<String> getListUsernameOrdered();
    List<Order> findAll();
    List<Order> getAllOfOrderToday(Date date);
    List<Order> findNotByCreateDate(Date toNow);
    List<Order> findByCreateDateBetween(Date from, Date to);
    List<Order> findAllByEmailOrPhone(String email, String phone);
    List<Order> findAllByPriceBetween();
    List<Order> findByStatus(Integer statusId);
    List<Order> findByStatusAndAccount(Integer id, Account account);
}
