package com.fastshop.net.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.CatReport;
import com.fastshop.net.model.OrderDetail;
import com.fastshop.net.service.OrderDetailService;

@CrossOrigin("*")
@RestController
public class RestOrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/rest/orderdetails")
    public List<OrderDetail> getAll() {
        return orderDetailService.findAll();
    }

    @GetMapping("/rest/orderdetails/{id}")
    public OrderDetail getOrderId(@PathVariable("id") Long id) {
        return orderDetailService.findById(id);
    }

    @GetMapping("/rest/orderdetails/category/report")
    public List<CatReport> reportCategory() {
        return orderDetailService.getNumberOrderedEachCategory();
    }

    @PostMapping("/rest/orderdetails")
    public OrderDetail create(@RequestBody OrderDetail orderDetail) {
        orderDetailService.save(orderDetail);
        return orderDetail;
    }

    @PutMapping("/rest/orderdetails/{id}")
    public OrderDetail put(@PathVariable("id") Long id, @RequestBody OrderDetail orderDetail) {
        if (orderDetailService.findAll().contains(orderDetail)) {
            orderDetailService.save(orderDetail);
        }
        return orderDetail;
    }

    @DeleteMapping("/rest/orderdetails/{id}")
    public void delete(@PathVariable("id") Long id) {
        orderDetailService.deleteById(id);
    }
}
