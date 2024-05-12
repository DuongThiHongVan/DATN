package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Discount;

public interface DiscountService {
    void save(Discount discount);
    Discount findById(String id);
    List<Discount> findByAll();
    List<Discount> findByDolarNotNull();
    List<Discount> findByFreeNotNull();
    List<Discount> findByFreeEqualNumber(Double free);
    List<Discount> findByDiscountExpiry();
    List<Discount> findByDiscountUnexpiry();
}
