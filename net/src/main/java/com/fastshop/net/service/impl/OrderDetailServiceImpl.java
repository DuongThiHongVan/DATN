package com.fastshop.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.CatReport;
import com.fastshop.net.model.OrderDetail;
import com.fastshop.net.model.Product;
import com.fastshop.net.repository.OrderDetailDAO;
import com.fastshop.net.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailDAO.save(orderDetail);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailDAO.deleteById(id);
    }

    @Override
    public OrderDetail findById(Long id) {
        return orderDetailDAO.findById(id).get();
    }

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailDAO.findAll();
    }

    @Override
    public Double getTotalRevenue() {
        try {
            return orderDetailDAO.getTotalRevenue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public int getTotalOrder() {
        try {
            return orderDetailDAO.getTotalOrder();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<String> getTop3BestSelling() {
        return orderDetailDAO.getTop3BestSelling();
    }

    @Override
    public Integer countByProduct(Product product) {
        return orderDetailDAO.countByProduct(product);
    }

    @Override
    public Double getTotalRevenueLast() {
        return orderDetailDAO.getTotalRevenueLast();
    }

    @Override
    public Integer getTotalOrderLast() {
        return orderDetailDAO.getTotalOrderLast();
    }

    @Override
    public List<CatReport> getNumberOrderedEachCategory() {
        return orderDetailDAO.getNumberOrderedEachCategory();
    }
}