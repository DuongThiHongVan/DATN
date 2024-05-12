package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Category;
import com.fastshop.net.model.Product;

public interface CategoryService {
    void save(Category category);
    Category findById(String id);
    List<Category> findAll();
    List<Category> findByStatus(Boolean status);
    List<Product> getOneProductEachCategories(int number);
    void updateProductAvaiableFromCategory(String id, Boolean avaiable);  // cho nay da sua
}
