package com.fastshop.net.controller.rest;

import java.util.Date;
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

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Order;
import com.fastshop.net.model.OrderDetail;
import com.fastshop.net.repository.OrderDetailDAO;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.OrderService;

@CrossOrigin("*")
@RestController
public class RestOrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    @Autowired
    OrderDetailDAO orderDetailDAO;

    @GetMapping("/rest/orders")
    public List<Order> getAll() {
        return orderService.findAll();
    }

    @GetMapping("/rest/orders/{id}")
    public Order getOrderId(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/rest/orders/status/{id}")
    public List<Order> getOrderStatusId(@PathVariable("id") Integer id) {
        return orderService.findByStatus(id);
    }

    @GetMapping("/rest/orders/today")
    public List<Order> today() {
        return orderService.getAllOfOrderToday(new Date());
    }

    @GetMapping("/rest/orders/year/{year}")
    public Double getTotalRevenueYear(@PathVariable("year") int year) {
        return orderService.totalRevenueByYear(year);
    }

    @GetMapping("/rest/orders/status/{username}/{id}")
    public List<Order> getOrderStatusId(@PathVariable("id") Integer id, @PathVariable("username") String username) {
        Account account = accountService.findByUsername(username);
        return orderService.findByStatusAndAccount(id, account);
    }

    @GetMapping("/rest/order/details/{id}")
    public List<OrderDetail> gDetailsWithOrder(@PathVariable("id") Long id) {
        Order order = orderService.findById(id);
        return orderDetailDAO.findByOrder(order);
    }

    @PostMapping("/rest/orders")
    public Order create(@RequestBody Order order) {
        if (!orderService.findAll().contains(order)) {
            orderService.save(order);
        }
        return order;
    }

    @PutMapping("/rest/orders/{id}")
    public Order put(@PathVariable("id") Long id, @RequestBody Order order) {
        if (orderService.findAll().contains(order)) {
            orderService.save(order);
        }
        return order;
    }

    @DeleteMapping("/rest/orders/{id}")
    public void delete(@PathVariable("id") Long id) {
        orderService.deleteById(id);
    }
}
