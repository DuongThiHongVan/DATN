package com.fastshop.net.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Category;
import com.fastshop.net.model.Comment;
import com.fastshop.net.model.Product;
import com.fastshop.net.repository.CategoryDAO;
import com.fastshop.net.repository.ProductDAO;
import com.fastshop.net.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductDAO productDAO;
    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productDAO.findById(id).get();
    }

    @Override
    public List<Product> findByCategoryId(String cid) {
        Category category = categoryDAO.findById(cid).get();
        return productDAO.findByCategory(category);
    }

    @Override
    public void save(Product product) {
        productDAO.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        productDAO.deleteById(id);
    }

    @Override
    public List<Product> findByKeywordName(String kw) {
        return productDAO.findByKeywordName(kw);
    }

    @Override
    public List<Product> findByFilter(Integer rate, String cateId, double priceFrom, double priceTo) {
        List<Product> list1 = productDAO.findByFilter(cateId, priceFrom, priceTo);
        if (rate == 0) {
            if (cateId.isEmpty()) {
                return productDAO.findAll()
                                 .stream()
                                 .filter(p -> p.getPrice() >= priceFrom && p.getPrice() <= priceTo)
                                 .collect(Collectors.toList());
            }
            return list1.stream().filter(item -> item.getCategory().getId().equals(cateId))
                                 .collect(Collectors.toList());
        }
        else {
            List<Product> list2 = new ArrayList<>();
            for (Product product : list1) {
                if (product.getComments().size() > 0) {
                    for (Comment c : product.getComments()) {
                        if (c.getRate() <= rate && list2.contains(product) == false) {
                            list2.add(product);
                        }
                    }
                }
            }
            return list2;
        }
    }
}
