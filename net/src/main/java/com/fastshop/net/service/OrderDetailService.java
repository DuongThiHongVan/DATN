package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.CatReport;
import com.fastshop.net.model.OrderDetail;
import com.fastshop.net.model.Product;

public interface OrderDetailService {
    void save(OrderDetail orderDetail);
    void deleteById(Long id);
    OrderDetail findById(Long id);
    List<OrderDetail> findAll();
    Double getTotalRevenue();
    int getTotalOrder();
    List<String> getTop3BestSelling();
    List<CatReport> getNumberOrderedEachCategory();
    Integer countByProduct(Product product);
    Double getTotalRevenueLast();
    Integer getTotalOrderLast();
}
