package com.fastshop.net.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Category;
import com.fastshop.net.model.Product;
import com.fastshop.net.repository.CategoryDAO;
import com.fastshop.net.repository.ProductDAO;
import com.fastshop.net.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    ProductDAO productDAO;

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public void save(Category category) {
        categoryDAO.save(category);
    }

    @Override
    public void updateProductAvaiableFromCategory(String id, Boolean avaiable) {
        Category category = categoryDAO.findById(id).get();
        category.setStatus(avaiable);
        categoryDAO.save(category);
        // List<Product> lists = category.getProducts();
        // for (Product product : lists) {
        //     product.setAvailable(product.getNumber() == 0 ? false : avaiable);
        //     productDAO.save(product);
        // }
    }

    @Override
    public List<Product> getOneProductEachCategories(int number) {
        List<Product> list = new ArrayList<>();
        List<Category> categories = categoryDAO.findAll();
        int size = categories.size();

        for (int i = 0; i < Math.min(size, number); i++) {
            List<Product> products = categories.get(i).getProducts();
            if (!products.isEmpty()) {
                list.add(products.get(0));
            }
        }
        return list;
    }


    @Override
    public List<Category> findByStatus(Boolean status) {
        return categoryDAO.findByStatus(status);
    }

    @Override
    public Category findById(String id) {
        return categoryDAO.findById(id).get();
    }
    
}
