package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fastshop.net.model.Category;
import com.fastshop.net.model.CategoryDetail;

public interface CategoryDetailDAO extends JpaRepository<CategoryDetail, String> {
    @Query("SELECT o FROM CategoryDetail o WHERE o.category = ?1")
    List<CategoryDetail> findByCategory(Category category);

    @Query("SELECT o.property FROM CategoryDetail o WHERE o.category = ?1")
    List<String> getPropertyByCategory(Category category);
}
