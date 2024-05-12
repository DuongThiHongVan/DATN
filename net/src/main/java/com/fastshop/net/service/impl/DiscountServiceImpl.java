package com.fastshop.net.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Discount;
import com.fastshop.net.repository.DiscountDAO;
import com.fastshop.net.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    DiscountDAO discountDAO;

    @Override
    public void save(Discount discount) {
        discountDAO.save(discount);
    }

    @Override
    public Discount findById(String id) {
        return discountDAO.findById(id).get();
    }

    @Override
    public List<Discount> findByAll() {
        return discountDAO.findAll();
    }

    @Override
    public List<Discount> findByDolarNotNull() {
        return discountDAO.findByDolarNotNull();
    }

    @Override
    public List<Discount> findByFreeNotNull() {
        return discountDAO.findByFreeNotNull();
    }

    @Override
    public List<Discount> findByFreeEqualNumber(Double free) {
        return discountDAO.findByFreeEqualNumber(free);
    }

    /**
     *  0: if both dates are equal.
     *  A value less than 0: if the date is before the argument date.
     *  A value greater than 0: if the date is after the argument date.
     */
    @Override
    public List<Discount> findByDiscountExpiry() {
        return findByAll().stream()
                          .filter(item -> item.getDateEnd().compareTo(new Date()) < 0)
                          .collect(Collectors.toList());
    }

    @Override
    public List<Discount> findByDiscountUnexpiry() {
        return findByAll().stream()
                          .filter(item -> item.getDateEnd().compareTo(new Date()) >= 0)
                          .collect(Collectors.toList());
    }
}
