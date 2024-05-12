package com.fastshop.net.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.Discount;
import com.fastshop.net.service.DiscountService;

@RestController
public class RestDiscountController {
    @Autowired
    DiscountService discountService;

    @GetMapping("/rest/discounts/all")
    public List<Discount> findAll() {
        return discountService.findByAll();
    }

    @GetMapping("/rest/discounts/{id}")
    public Discount findById(@PathVariable("id") String id) {
        try {
            return discountService.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/rest/discounts/expiry")
    public List<Discount> expiry() {
        return discountService.findByDiscountExpiry();
    }

    @GetMapping("/rest/discounts/unexpiry")
    public List<Discount> unexpiry() {
        return discountService.findByDiscountUnexpiry();
    }

    @GetMapping("/rest/discounts/dolar")
    public List<Discount> dolar() {
        return discountService.findByDolarNotNull();
    }

    @GetMapping("/rest/discounts/free")
    public List<Discount> free() {
        return discountService.findByFreeNotNull();
    }

    @GetMapping("/rest/discounts/free/{value}")
    public List<Discount> freeTo(@PathVariable("value") Double value) {
        return discountService.findByFreeEqualNumber(value);
    }

    @PostMapping("/rest/discounts")
    public void save(@RequestBody Discount discount) {
        discountService.save(discount);
    }
}
