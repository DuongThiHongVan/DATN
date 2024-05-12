package com.fastshop.net.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.CartItem;
import com.fastshop.net.service.ProductService;

@CrossOrigin("*")
@RestController
public class RestCartController {
    @Autowired
    ProductService productSevice;

    @GetMapping("/rest/get/cartitems")
    public Map<Integer, CartItem> getCartItems() {
        Map<Integer, CartItem> map = new HashMap<>();
        productSevice.findAll().forEach(product -> {
            CartItem cartItem = new CartItem(product.getId(), product, 1);
            map.put(product.getId(), cartItem);
        });

        return map;
    }
}
