package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastshop.net.model.Category;

public interface CategoryDAO extends JpaRepository<Category, String> {
    Boolean getStatusById(String id);
    List<Category> findByStatus(Boolean status);
}
