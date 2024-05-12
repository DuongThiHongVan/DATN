package com.fastshop.net.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.Category;
import com.fastshop.net.model.CategoryDetail;
import com.fastshop.net.service.CategoryDetailService;
import com.fastshop.net.service.CategoryService;

@CrossOrigin("*")
@RestController
public class RestCategoryDetailController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryDetailService categoryDetailService;

    @GetMapping("/rest/categorydetails/{category_id}")
    public List<CategoryDetail> findByCategoryId(@PathVariable("category_id") String category_id) {
        Category category = categoryService.findById(category_id);
        return categoryDetailService.findByCategory(category);
    }

    @GetMapping("/rest/categorydetails/properties/{category_id}")
    public List<String> getByCategoryId(@PathVariable("category_id") String category_id) {
        Category category = categoryService.findById(category_id);
        return categoryDetailService.getPropertyByCategory(category);
    }
}
