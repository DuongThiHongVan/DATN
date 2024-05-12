package com.fastshop.net.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.Category;
import com.fastshop.net.service.CategoryService;

@CrossOrigin("*")
@RestController
public class RestCategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/rest/categories")
    public List<Category> getAll(Model model) {
        return categoryService.findAll();
    }

    @GetMapping("/rest/categories/name")
    public List<String> getAllName() {
        return categoryService.findAll().stream()
                              .map(item -> item.getName())
                              .collect(Collectors.toList());
    }

    @PostMapping("/rest/categories")
    public Category post(@RequestBody Category category) {
        categoryService.save(category);
        return category;
    }

    @PutMapping("/rest/categories/{id}")
    public Category put(@PathVariable("id") String id, @RequestBody Category category) {
        categoryService.save(category);
        return category;
    }
}
